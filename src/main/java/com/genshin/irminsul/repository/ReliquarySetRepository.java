package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ReliquarySet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquarySetRepository extends MongoRepository<ReliquarySet, Integer> {}
