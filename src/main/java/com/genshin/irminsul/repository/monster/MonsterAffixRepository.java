package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterAffix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterAffixRepository extends MongoRepository<MonsterAffix, Integer> {}
