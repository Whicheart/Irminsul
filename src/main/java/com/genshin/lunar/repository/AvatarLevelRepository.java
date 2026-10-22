package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarLevelRepository extends MongoRepository<AvatarLevel, Integer> {}
