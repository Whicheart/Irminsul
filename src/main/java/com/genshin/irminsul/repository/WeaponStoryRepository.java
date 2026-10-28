package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.WeaponStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponStoryRepository extends MongoRepository<WeaponStory, Integer> {}
