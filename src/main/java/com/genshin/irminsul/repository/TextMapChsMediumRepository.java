package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.TextMapChsMedium;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextMapChsMediumRepository extends MongoRepository<TextMapChsMedium, String> {
    // 按哈希集合批量取文本
    List<TextMapChsMedium> findByIdIn(Collection<String> ids);
}
