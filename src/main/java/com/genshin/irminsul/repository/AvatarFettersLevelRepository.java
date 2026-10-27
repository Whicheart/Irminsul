package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarFettersLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarFettersLevelRepository extends MongoRepository<AvatarFettersLevel, Integer> {}
