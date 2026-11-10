package com.genshin.irminsul.util;

import com.genshin.irminsul.entity.TextMapChsMedium;
import com.genshin.irminsul.repository.TextMapChsMediumRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class TextMapChsUtil {
    // 单次 findByIdIn 的 id 数量上限，避免请求过大
    private static final int BATCH_SIZE = 500;

    // 负缓存哨兵：区分“查过但不存在”和“还没查过”
    private static final String MISSING = "";

    private final TextMapChsMediumRepository textMapRepository;
    private final Cache<String, String> textCache =
            Caffeine.newBuilder().maximumSize(200_000).build();

    public TextMapChsUtil(TextMapChsMediumRepository textMapRepository) {
        this.textMapRepository = textMapRepository;
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
            Map<String, String> found = new HashMap<>();
            for (TextMapChsMedium medium : textMapRepository.findByIdIn(chunk)) {
                found.put(medium.getId(), medium.getText());
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

    private static boolean isValid(String hash) {
        return hash != null && !hash.isEmpty() && !"0".equals(hash);
    }
}
