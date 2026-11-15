package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRelationshipRepository extends MongoRepository<MonsterRelationship, Integer> {}
