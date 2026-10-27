package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarCodex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCodexRepository extends MongoRepository<AvatarCodex, Integer> {}
