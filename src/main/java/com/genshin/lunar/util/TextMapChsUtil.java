package com.genshin.lunar.util;

import com.genshin.lunar.entity.TextMapChsMedium;
import com.genshin.lunar.repository.TextMapChsMediumRepository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class TextMapChsUtil {
    private final TextMapChsMediumRepository textMapRepo;

    private final Map<String, String> textMapCache = new ConcurrentHashMap<>();

    public TextMapChsUtil(TextMapChsMediumRepository textMapRepo) {
        this.textMapRepo = textMapRepo;
    }

    public String getText(String hash) {
        if (hash == null || hash.isEmpty() || "0".equals(hash)) {
            return null;
        }

        if (textMapCache.containsKey(hash)) {
            return textMapCache.get(hash);
        }

        TextMapChsMedium entity = textMapRepo.findById(hash).orElse(null);
        if (entity != null) {
            textMapCache.put(hash, entity.getText());
            return entity.getText();
        }

        return null;
    }
}
