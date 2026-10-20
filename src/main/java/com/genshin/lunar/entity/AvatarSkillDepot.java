package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "avatar_skill_depot")
public class AvatarSkillDepot {
    @Id
    private Integer id;

    @Field("skills")
    private List<Integer> skills;
    @Field("talents")
    private List<Integer> talents;
    @Field("energySkill")
    private Integer energySkill;

    @Field("proudSkillGroups")
    private List<ProudSkillGroup> proudSkillGroups;
    @Field("specialProudSkillGroups")
    private List<SpecialProudSkillGroup> specialProudSkillGroups;

    @Field("arkheType")
    private String arkheType;
    @Field("talentStarName")
    private String talentStarName;

    @Data
    public static class ProudSkillGroup {
        @Field("proudSkillGroupId")
        private Integer proudSkillGroupId;
        @Field("unlockPromoteLevel")
        private Integer unlockPromoteLevel;
    }

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
