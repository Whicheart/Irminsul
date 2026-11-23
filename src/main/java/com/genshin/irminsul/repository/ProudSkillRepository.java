package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.ProudSkill;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 天赋技能仓储，对应 proud_skill 集合，同一技能组包含 1~10 级配置 */
@Repository
public interface ProudSkillRepository extends MongoRepository<ProudSkill, Integer> {

    /**
     * 按技能组查询全部等级
     *
     * @param proudSkillGroupId 技能组 ID
     * @return 按等级升序的天赋配置
     */
    List<ProudSkill> findByProudSkillGroupIdOrderByLevelAsc(Integer proudSkillGroupId);

    /**
     * 批量查询多个技能组，并按技能组、等级升序返回
     *
     * @param proudSkillGroupIds 技能组 ID 集合
     * @return 天赋配置列表
     */
    List<ProudSkill> findByProudSkillGroupIdInOrderByProudSkillGroupIdAscLevelAsc(
            Collection<Integer> proudSkillGroupIds);
}
