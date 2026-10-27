package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarPromote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarPromoteRepository extends MongoRepository<AvatarPromote, Integer> {}
