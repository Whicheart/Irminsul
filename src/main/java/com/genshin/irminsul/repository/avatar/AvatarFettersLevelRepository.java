package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarFettersLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色好感等级仓储，对应 avatar_fetters_level 集合，记录每级所需好感经验 */
@Repository
public interface AvatarFettersLevelRepository extends MongoRepository<AvatarFettersLevel, Integer> {}
