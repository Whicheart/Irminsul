package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar, Integer> {}
