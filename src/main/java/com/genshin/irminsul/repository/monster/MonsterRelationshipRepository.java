package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 怪物关系仓储，对应 monster_relationship 集合，记录所属族群 */
@Repository
public interface MonsterRelationshipRepository extends MongoRepository<MonsterRelationship, Integer> {}
