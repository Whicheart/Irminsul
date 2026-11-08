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

    @Field("skills")
    private List<Integer> skills;

    @Field("subSkills")
    private List<Integer> subSkills;

    @Field("talents")
    private List<Integer> talents;

    @Field("energySkill")
    private Integer energySkill;

    @Field("leaderTalent")
    private Integer leaderTalent;

    @Field("attackModeSkill")
    private Integer attackModeSkill;

    @Field("extraAbilities")
    private List<String> extraAbilities;

    @Field("inherentProudSkillOpens")
    private List<InherentProudSkillOpen> inherentProudSkillOpens;

    @Field("specialProudSkillOpens")
    private List<SpecialProudSkillOpen> specialProudSkillOpens;

    @Field("arkheType")
    private String arkheType;

    @Field("talentStarName")
    private String talentStarName;

    @Field("skillDepotAbilityGroup")
    private String skillDepotAbilityGroup;

    @Data
    public static class InherentProudSkillOpen {

        @Field("proudSkillGroupId")
        private Integer proudSkillGroupId;

        @Field("needAvatarPromoteLevel")
        private Integer needAvatarPromoteLevel;
    }

    @Data
    public static class SpecialProudSkillOpen {

        @Field("proudSkillGroupId")
        private Integer proudSkillGroupId;
    }
}
