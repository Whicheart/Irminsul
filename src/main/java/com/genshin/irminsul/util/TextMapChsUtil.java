package com.genshin.irminsul.util;

import com.genshin.irminsul.entity.textmap.TextMapEntry;
import com.genshin.irminsul.repository.textmap.TextMapChsMediumRepository;
import com.genshin.irminsul.repository.textmap.TextMapChsRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/**
 * TextMap 文本解析组件
 *
 * <p>把种子数据中的文本哈希（TextMapHash）翻译为简体中文：先查 Caffeine
 * 本地缓存，未命中时按 500 条一批查询，先查中等表、缺失部分再回退到完整表；
 * 查不到的结果以空串负缓存，避免重复穿透
 */
@Component
public class TextMapChsUtil {
    // 单次 findByIdIn 的数量上限
    private static final int BATCH_SIZE = 500;

    // 负缓存区分
    private static final String MISSING = "";

    // 中等体积文本表仓储（中等常用文本）
    private final TextMapChsMediumRepository mediumRepository;
    // 全量文本表仓储（兜底查询）
    private final TextMapChsRepository chsRepository;
    // 哈希到文本的本地缓存，空串表示已确认缺失
    private final Cache<String, String> textCache =
            Caffeine.newBuilder().maximumSize(200_000).build();

    /** 构造器注入两个文本表仓储 */
    public TextMapChsUtil(TextMapChsMediumRepository mediumRepository, TextMapChsRepository chsRepository) {
        this.mediumRepository = mediumRepository;
        this.chsRepository = chsRepository;
    }

    /**
     * 解析单个文本哈希
     *
     * @param hash 文本哈希，允许为 null 或 "0"
     * @return 对应中文；哈希无效或缺失时返回 null
     */
    public String getText(String hash) {
        if (!isValid(hash)) {
            return null;
        }
        return getTexts(List.of(hash)).get(hash);
    }

    /**
     * 批量解析文本哈希
     *
     * @param hashes 文本哈希集合，允许为 null
     * @return 哈希到文本的映射；缺失条目的值为 null
     */
    public Map<String, String> getTexts(Collection<String> hashes) {
        Map<String, String> result = new HashMap<>();
        if (hashes == null || hashes.isEmpty()) {
            return result;
        }

        Set<String> pending = new LinkedHashSet<>();
        for (String hash : hashes) {
            if (!isValid(hash)) {
                continue;
            }
            String cached = textCache.getIfPresent(hash);
            if (cached != null) {
                result.put(hash, MISSING.equals(cached) ? null : cached);
            } else {
                pending.add(hash);
            }
        }

        List<String> keys = new ArrayList<>(pending);
        for (int start = 0; start < keys.size(); start += BATCH_SIZE) {
            List<String> chunk = keys.subList(start, Math.min(start + BATCH_SIZE, keys.size()));
            // Medium -> CHS -> 缺失
            Map<String, String> found = query(mediumRepository::findByIdIn, chunk);
            List<String> notInMedium =
                    chunk.stream().filter(key -> !found.containsKey(key)).toList();
            if (!notInMedium.isEmpty()) {
                found.putAll(query(chsRepository::findByIdIn, notInMedium));
            }
            for (String key : chunk) {
                String text = found.get(key);
                if (text != null) {
                    textCache.put(key, text);
                    result.put(key, text);
                } else {
                    textCache.put(key, MISSING);
                    result.put(key, null);
                }
            }
        }
        return result;
    }

    /**
     * 执行一次批量查询并转成哈希到文本的映射
     *
     * @param loader 仓储的批量查询方法
     * @param chunk  单批哈希，长度不超过 BATCH_SIZE
     * @return 本批查询结果
     */
    private static Map<String, String> query(
            Function<Collection<String>, List<? extends TextMapEntry>> loader, List<String> chunk) {
        Map<String, String> found = new HashMap<>();
        for (TextMapEntry entry : loader.apply(chunk)) {
            found.put(entry.getId(), entry.getText());
        }
        return found;
    }

    /**
     * 判断文本哈希是否可用
     *
     * @param hash 待校验的哈希
     * @return null、空串与 "0" 均视为无效
     */
    private static boolean isValid(String hash) {
        return hash != null && !hash.isEmpty() && !"0".equals(hash);
    }
}
