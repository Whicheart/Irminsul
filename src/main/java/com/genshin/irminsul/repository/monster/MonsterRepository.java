package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends MongoRepository<Monster, Integer> {}
