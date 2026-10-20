package com.genshin.lunar.repository;

import com.genshin.lunar.entity.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar, Integer> {}