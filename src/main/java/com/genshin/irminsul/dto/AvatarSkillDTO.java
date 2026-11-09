package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarSkillDTO {

    private Integer id;

    // 主动/固有 天赋
    private List<CombatSkill> combatSkills;
    private List<PassiveSkill> passiveSkills;

    // 老角色加强
    private List<SpecialProudSkill> specialProudSkills;

    @Data
    public static class CombatSkill {

        private Integer id;

        // 基础信息
        private String name;
        private String desc;
        private String extraDesc;
        private String descExtra;
        private String loreDesc;

        // 图像资源
        private String iconName;

        // 技能倍率
        private List<CombatSkillData> combatSkillDataList;
    }

    @Data
    public static class PassiveSkill {

        private Integer id;

        // 基础信息
        private String name;
        private String desc;
        private String extraDesc;
        private String descExtra;
        private String loreDesc;

        // 图像资源
        private String iconName;
    }

    @Data
    public static class SpecialProudSkill {

        private Integer id;

        // 基础信息
        private String name;
        private String desc;
        private String extraDesc;
        private String descExtra;
        private String loreDesc;

        // 图像资源
        private String iconName;

        // 技能倍率
        private List<CombatSkillData> combatSkillDataList;
    }

    @Data
    public static class CombatSkillData {

        private Integer level;
        private List<String> paramDescList;
        private List<Double> paramList;
    }
}
