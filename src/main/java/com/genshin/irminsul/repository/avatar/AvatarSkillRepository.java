package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarSkill;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 角色技能仓储，对应 avatar_skill 集合，提供展示字段的投影查询 */
@Repository
public interface AvatarSkillRepository extends MongoRepository<AvatarSkill, Integer> {

    /** 技能展示需要的字段子集 */
    interface AvatarSkillBriefView {

        Integer getId();

        String getNameTextMapHash();

        String getDescTextMapHash();

        String getExtraDescTextMapHash();

        String getDescExtraTextMapHash();

        String getSkillIcon();

        Integer getProudSkillGroupId();
    }

    /**
     * 按 ID 集合批量查询技能简要投影
     *
     * @param ids 技能 ID 集合
     * @return 技能投影列表
     */
    @Query(
            value = "{ '_id': { $in: ?0 } }",
            fields = "{ 'nameTextMapHash': 1, 'descTextMapHash': 1, 'extraDescTextMapHash': 1, "
                    + "'descExtraTextMapHash': 1, 'skillIcon': 1, 'proudSkillGroupId': 1 }")
    List<AvatarSkillBriefView> findBriefByIdIn(Collection<Integer> ids);
}
