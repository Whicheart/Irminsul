package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 怪物成长曲线仓储，对应 monster_curve 集合 */
@Repository
public interface MonsterCurveRepository extends MongoRepository<MonsterCurve, Integer> {}
