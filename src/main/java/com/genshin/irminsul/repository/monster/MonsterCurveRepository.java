package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterCurveRepository extends MongoRepository<MonsterCurve, Integer> {}
