package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.WeaponCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 武器成长曲线仓储，对应 weapon_curve 集合，主键为等级 */
@Repository
public interface WeaponCurveRepository extends MongoRepository<WeaponCurve, Integer> {}
