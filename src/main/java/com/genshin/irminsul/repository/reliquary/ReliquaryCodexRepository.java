package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.ReliquaryCodex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 圣遗物图鉴仓储，对应 reliquary_codex 集合 */
@Repository
public interface ReliquaryCodexRepository extends MongoRepository<ReliquaryCodex, Integer> {}
