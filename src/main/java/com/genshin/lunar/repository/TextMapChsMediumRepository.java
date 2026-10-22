package com.genshin.lunar.repository;

import com.genshin.lunar.entity.TextMapChsMedium;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TextMapChsMediumRepository extends MongoRepository<TextMapChsMedium, String> {
    // 按哈希集合批量取文本
    List<TextMapChsMedium> findByIdIn(Collection<String> ids);
}
