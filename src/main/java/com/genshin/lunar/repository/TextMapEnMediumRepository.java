package com.genshin.lunar.repository;

import com.genshin.lunar.entity.TextMapEnMedium;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface TextMapEnMediumRepository extends MongoRepository<TextMapEnMedium, String> {
    // 按哈希集合批量取文本
    List<TextMapEnMedium> findByIdIn(Collection<String> ids);
}
