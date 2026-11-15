package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterMultiPlayer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterMultiPlayerRepository extends MongoRepository<MonsterMultiPlayer, Integer> {}
