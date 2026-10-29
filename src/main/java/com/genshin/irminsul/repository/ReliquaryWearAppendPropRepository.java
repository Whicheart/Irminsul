package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ReliquaryWearAppendProp;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryWearAppendPropRepository extends MongoRepository<ReliquaryWearAppendProp, ObjectId> {}
