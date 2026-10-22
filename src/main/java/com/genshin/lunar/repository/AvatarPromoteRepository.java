package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarPromote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarPromoteRepository extends MongoRepository<AvatarPromote, Integer> {}
