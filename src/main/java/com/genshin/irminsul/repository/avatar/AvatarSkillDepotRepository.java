package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarSkillDepot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色技能组仓储，对应 avatar_skill_depot 集合 */
@Repository
public interface AvatarSkillDepotRepository extends MongoRepository<AvatarSkillDepot, Integer> {}
