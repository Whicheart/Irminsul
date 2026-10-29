package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ReliquaryWearMainProp;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryWearMainPropRepository extends MongoRepository<ReliquaryWearMainProp, ObjectId> {}
