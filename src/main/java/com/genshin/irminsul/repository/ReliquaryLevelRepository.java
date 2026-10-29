package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ReliquaryLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryLevelRepository extends MongoRepository<ReliquaryLevel, Integer> {}
