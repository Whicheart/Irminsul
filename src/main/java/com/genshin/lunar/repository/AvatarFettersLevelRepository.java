package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarFettersLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarFettersLevelRepository extends MongoRepository<AvatarFettersLevel, Integer> {}