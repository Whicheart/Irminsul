package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarTraceEffect;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarTraceEffectRepository extends MongoRepository<AvatarTraceEffect, Integer> {}