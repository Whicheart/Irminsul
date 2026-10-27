package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.MaterialCodex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialCodexRepository extends MongoRepository<MaterialCodex, Integer> {
    // 按物品 ID 查图鉴条目
    MaterialCodex findByMaterialId(Integer materialId);
}
