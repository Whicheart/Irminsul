package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarSkillDepot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarSkillDepotRepository extends MongoRepository<AvatarSkillDepot, Integer> {}
