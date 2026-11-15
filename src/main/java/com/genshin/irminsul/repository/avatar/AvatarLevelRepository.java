package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarLevelRepository extends MongoRepository<AvatarLevel, Integer> {}
