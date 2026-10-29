package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ReliquaryMainProp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryMainPropRepository extends MongoRepository<ReliquaryMainProp, Integer> {}
