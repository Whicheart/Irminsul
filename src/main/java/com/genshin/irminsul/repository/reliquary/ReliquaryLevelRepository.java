package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.ReliquaryLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 圣遗物等级配置仓储，对应 reliquary_level 集合，主键为星级 */
@Repository
public interface ReliquaryLevelRepository extends MongoRepository<ReliquaryLevel, Integer> {}
