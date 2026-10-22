package com.genshin.lunar.repository;

import com.genshin.lunar.entity.MaterialCodex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialCodexRepository extends MongoRepository<MaterialCodex, Integer> {
    // 按物品 ID 查图鉴条目
    MaterialCodex findByMaterialId(Integer materialId);
}
