package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.ReliquarySet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 圣遗物套装仓储，对应 reliquary_set 集合 */
@Repository
public interface ReliquarySetRepository extends MongoRepository<ReliquarySet, Integer> {}
