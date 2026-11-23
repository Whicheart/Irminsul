package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AnimalCodex;
import org.springframework.data.mongodb.repository.MongoRepository;

/** 敌对生物图鉴仓储，对应 animal_codex 集合 */
public interface AnimalCodexRepository extends MongoRepository<AnimalCodex, Integer> {}
