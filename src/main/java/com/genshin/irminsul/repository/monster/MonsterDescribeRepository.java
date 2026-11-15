package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterDescribe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterDescribeRepository extends MongoRepository<MonsterDescribe, Integer> {}
