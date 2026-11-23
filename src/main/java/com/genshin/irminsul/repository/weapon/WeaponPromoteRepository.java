package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.WeaponPromote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 武器突破配置仓储，对应 weapon_promote 集合 */
@Repository
public interface WeaponPromoteRepository extends MongoRepository<WeaponPromote, Integer> {}
