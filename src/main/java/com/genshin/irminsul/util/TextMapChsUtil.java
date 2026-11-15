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

@Component
public class TextMapChsUtil {
    // 单次 findByIdIn 的数量上限
    private static final int BATCH_SIZE = 500;

    // 负缓存区分
    private static final String MISSING = "";

    private final TextMapChsMediumRepository mediumRepository;
    private final TextMapChsRepository chsRepository;
    private final Cache<String, String> textCache =
            Caffeine.newBuilder().maximumSize(200_000).build();

    public TextMapChsUtil(TextMapChsMediumRepository mediumRepository, TextMapChsRepository chsRepository) {
        this.mediumRepository = mediumRepository;
        this.chsRepository = chsRepository;
    }

    public String getText(String hash) {
        if (!isValid(hash)) {
            return null;
        }
        return getTexts(List.of(hash)).get(hash);
    }

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

    private static Map<String, String> query(
            Function<Collection<String>, List<? extends TextMapEntry>> loader, List<String> chunk) {
        Map<String, String> found = new HashMap<>();
        for (TextMapEntry entry : loader.apply(chunk)) {
            found.put(entry.getId(), entry.getText());
        }
        return found;
    }

    private static boolean isValid(String hash) {
        return hash != null && !hash.isEmpty() && !"0".equals(hash);
    }
}
