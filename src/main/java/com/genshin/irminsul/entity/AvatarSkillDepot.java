package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_skill_depot")
public class AvatarSkillDepot {
    @Id
    private Integer id;

    // 技能与命座所依赖的技能 ID
    @Field("skills")
    private List<Integer> skills;

    @Field("talents")
    private List<Integer> talents;

    @Field("energySkill")
    private Integer energySkill;

    // 固有天赋组
    @Field("proudSkillGroups")
    private List<ProudSkillGroup> proudSkillGroups;

    @Field("specialProudSkillGroups")
    private List<SpecialProudSkillGroup> specialProudSkillGroups;

    // 始基力与命之座名称
    @Field("arkheType")
    private String arkheType;

    @Field("talentStarName")
    private String talentStarName;

    // 普通固有天赋组
    @Data
    public static class ProudSkillGroup {
        @Field("proudSkillGroupId")
        private Integer proudSkillGroupId;

        @Field("unlockPromoteLevel")
        private Integer unlockPromoteLevel;
    }

    // 特殊固有天赋组
    @Data
    public static class SpecialProudSkillGroup {
        @Field("proudSkillGroupId")
        private Integer proudSkillGroupId;
    }

    /*
    // === 一个只有旅行者才有的混淆字段 ===
    @Field("BHAHKMCMDLO")
    private Integer BHAHKMCMDLO;
    // === 同样只有旅行者有，不知道有啥用 ===
    @Field("skillDepotAbilityGroup")
    private String skillDepotAbilityGroup;
     */
}
