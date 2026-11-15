package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterSpecialName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterSpecialNameRepository extends MongoRepository<MonsterSpecialName, Integer> {}
