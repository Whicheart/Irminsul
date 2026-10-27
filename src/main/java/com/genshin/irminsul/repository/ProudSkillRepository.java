package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ProudSkill;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProudSkillRepository extends MongoRepository<ProudSkill, Integer> {
    // 按组 ID 查所有等级的天赋数据，并按等级升序排列
    List<ProudSkill> findByProudSkillGroupIdOrderByLevelAsc(Integer proudSkillGroupId);
}
