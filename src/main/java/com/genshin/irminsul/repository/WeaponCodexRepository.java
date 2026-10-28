package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.WeaponCodex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponCodexRepository extends MongoRepository<WeaponCodex, Integer> {
    // 按武器 ID 查图鉴条目
    WeaponCodex findByWeaponId(Integer weaponId);
}
