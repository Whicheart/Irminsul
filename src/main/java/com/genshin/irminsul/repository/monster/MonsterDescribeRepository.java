package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterDescribe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 怪物图鉴描述仓储，对应 monster_describe 集合，关联名称、称号与特殊名 */
@Repository
public interface MonsterDescribeRepository extends MongoRepository<MonsterDescribe, Integer> {}
