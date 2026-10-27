package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarSkillDepot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarSkillDepotRepository extends MongoRepository<AvatarSkillDepot, Integer> {}
