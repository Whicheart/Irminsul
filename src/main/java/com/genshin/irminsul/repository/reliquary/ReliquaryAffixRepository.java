package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.ReliquaryAffix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 圣遗物副词条仓储，对应 reliquary_affix 集合 */
@Repository
public interface ReliquaryAffixRepository extends MongoRepository<ReliquaryAffix, Integer> {}
