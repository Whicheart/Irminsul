package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.ReliquaryLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryLevelRepository extends MongoRepository<ReliquaryLevel, Integer> {}
