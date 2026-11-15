package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarSkillDepot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarSkillDepotRepository extends MongoRepository<AvatarSkillDepot, Integer> {}
