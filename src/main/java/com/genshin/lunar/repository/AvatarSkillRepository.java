package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarSkill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarSkillRepository extends MongoRepository<AvatarSkill, Integer> {}
