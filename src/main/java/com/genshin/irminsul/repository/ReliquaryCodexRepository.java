package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ReliquaryCodex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryCodexRepository extends MongoRepository<ReliquaryCodex, Integer> {}
