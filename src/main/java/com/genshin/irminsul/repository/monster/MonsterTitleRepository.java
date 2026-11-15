package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterTitle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterTitleRepository extends MongoRepository<MonsterTitle, Integer> {}
