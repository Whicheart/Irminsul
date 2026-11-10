package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ProudSkill;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProudSkillRepository extends MongoRepository<ProudSkill, Integer> {

    List<ProudSkill> findByProudSkillGroupIdOrderByLevelAsc(Integer proudSkillGroupId);

    List<ProudSkill> findByProudSkillGroupIdInOrderByProudSkillGroupIdAscLevelAsc(
            Collection<Integer> proudSkillGroupIds);
}
