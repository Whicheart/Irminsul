package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarSkill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarSkillRepository extends MongoRepository<AvatarSkill, Integer> {}
