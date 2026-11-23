package com.genshin.irminsul.entity.avatar;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色技能组配置 */
@Data
@Document(collection = "avatar_skill_depot")
public class AvatarSkillDepot {

    // 技能组 ID
    @Id
    private Integer id;

    // 技能 ID 列表
    @Field("skills")
    private List<Integer> skills;

    // 子技能 ID 列表
    @Field("subSkills")
    private List<Integer> subSkills;

    // 命座 ID 列表
    @Field("talents")
    private List<Integer> talents;

    // 元素爆发技能 ID
    @Field("energySkill")
    private Integer energySkill;

    // 队长天赋 ID
    @Field("leaderTalent")
    private Integer leaderTalent;

    // 攻击模式技能 ID
    @Field("attackModeSkill")
    private Integer attackModeSkill;

    // 额外能力名
    @Field("extraAbilities")
    private List<String> extraAbilities;

    // 固有天赋解锁配置
    @Field("inherentProudSkillOpens")
    private List<InherentProudSkillOpen> inherentProudSkillOpens;

    // 特殊天赋解锁配置
    @Field("specialProudSkillOpens")
    private List<SpecialProudSkillOpen> specialProudSkillOpens;

    // 始基力类型
    @Field("arkheType")
    private String arkheType;

    // 天赋星级名
    @Field("talentStarName")
    private String talentStarName;

    // 技能组能力分组
    @Field("skillDepotAbilityGroup")
    private String skillDepotAbilityGroup;

    // 固有天赋解锁配置
    @Data
    public static class InherentProudSkillOpen {

        // 天赋技能组 ID
        @Field("proudSkillGroupId")
        private Integer proudSkillGroupId;

        // 所需突破等级
        @Field("needAvatarPromoteLevel")
        private Integer needAvatarPromoteLevel;
    }

    // 特殊天赋解锁配置
    @Data
    public static class SpecialProudSkillOpen {

        // 天赋技能组 ID
        @Field("proudSkillGroupId")
        private Integer proudSkillGroupId;
    }
}
