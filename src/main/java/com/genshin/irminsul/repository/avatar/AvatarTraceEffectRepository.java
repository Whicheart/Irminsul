package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarTraceEffect;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarTraceEffectRepository extends MongoRepository<AvatarTraceEffect, Integer> {}
