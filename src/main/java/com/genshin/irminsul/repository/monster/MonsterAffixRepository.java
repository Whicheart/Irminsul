package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterAffix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 怪物词缀仓储，对应 monster_affix 集合 */
@Repository
public interface MonsterAffixRepository extends MongoRepository<MonsterAffix, Integer> {}
