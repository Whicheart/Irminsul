package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarCodex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCodexRepository extends MongoRepository<AvatarCodex, Integer> {}
