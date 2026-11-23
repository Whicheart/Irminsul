package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterMultiPlayer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 怪物联机参数仓储，对应 monster_multi_player 集合 */
@Repository
public interface MonsterMultiPlayerRepository extends MongoRepository<MonsterMultiPlayer, Integer> {}
