package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.WeaponCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponCurveRepository extends MongoRepository<WeaponCurve, Integer> {}
