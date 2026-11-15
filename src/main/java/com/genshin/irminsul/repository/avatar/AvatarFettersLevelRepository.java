package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarFettersLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarFettersLevelRepository extends MongoRepository<AvatarFettersLevel, Integer> {}
