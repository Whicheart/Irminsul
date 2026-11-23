package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色等级经验仓储，对应 avatar_level 集合，主键为等级 */
@Repository
public interface AvatarLevelRepository extends MongoRepository<AvatarLevel, Integer> {}
