package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.WeaponLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 武器等级经验仓储，对应 weapon_level 集合 */
@Repository
public interface WeaponLevelRepository extends MongoRepository<WeaponLevel, Integer> {}
