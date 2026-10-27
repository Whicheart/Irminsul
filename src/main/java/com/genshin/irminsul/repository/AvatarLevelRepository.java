package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarLevelRepository extends MongoRepository<AvatarLevel, Integer> {}
