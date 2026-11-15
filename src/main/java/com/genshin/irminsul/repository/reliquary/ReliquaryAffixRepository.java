package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.ReliquaryAffix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryAffixRepository extends MongoRepository<ReliquaryAffix, Integer> {}
