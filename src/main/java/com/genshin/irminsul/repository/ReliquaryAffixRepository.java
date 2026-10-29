package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ReliquaryAffix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryAffixRepository extends MongoRepository<ReliquaryAffix, Integer> {}
