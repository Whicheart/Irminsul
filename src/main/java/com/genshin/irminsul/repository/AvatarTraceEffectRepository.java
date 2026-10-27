package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarTraceEffect;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarTraceEffectRepository extends MongoRepository<AvatarTraceEffect, Integer> {}
