package com.genshin.irminsul.repository.textmap;

import com.genshin.irminsul.entity.textmap.TextMapChs;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextMapChsRepository extends MongoRepository<TextMapChs, String> {
    // 按哈希集合批量取文本
    List<TextMapChs> findByIdIn(Collection<String> ids);
}
