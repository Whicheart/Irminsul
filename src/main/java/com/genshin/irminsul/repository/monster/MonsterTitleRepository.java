package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterTitle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 怪物称号仓储，对应 monster_title 集合 */
@Repository
public interface MonsterTitleRepository extends MongoRepository<MonsterTitle, Integer> {}
