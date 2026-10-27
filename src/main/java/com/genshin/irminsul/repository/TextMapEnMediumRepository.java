package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.TextMapEnMedium;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TextMapEnMediumRepository extends MongoRepository<TextMapEnMedium, String> {
    // 按哈希集合批量取文本
    List<TextMapEnMedium> findByIdIn(Collection<String> ids);
}
