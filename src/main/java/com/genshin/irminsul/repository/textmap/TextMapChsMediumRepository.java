package com.genshin.irminsul.repository.textmap;

import com.genshin.irminsul.entity.textmap.TextMapChsMedium;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 简体中文文本表（中等体积），对应 text_map_chs_medium 集合 */
@Repository
public interface TextMapChsMediumRepository extends MongoRepository<TextMapChsMedium, String> {
    // 按哈希集合批量取文本
    List<TextMapChsMedium> findByIdIn(Collection<String> ids);
}
