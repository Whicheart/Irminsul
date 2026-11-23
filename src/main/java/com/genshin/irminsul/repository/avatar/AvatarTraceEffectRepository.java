package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarTraceEffect;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色特效仓储，对应 avatar_trace_effect 集合 */
@Repository
public interface AvatarTraceEffectRepository extends MongoRepository<AvatarTraceEffect, Integer> {}
