package com.genshin.lunar.dto;

import lombok.Data;

import java.util.List;

@Data
public class AvatarSkillDTO {
    private Integer id;
    private List<CombatSkill> combatSkills;
    private List<PassiveSkill> passiveSkills;
    private List<SpecialProudSkill> specialProudSkills;

    @Data
    public static class CombatSkill {
        private Integer id;
        private String name;
        private String desc;
        private String extraDesc;
        private String descExtra;
        private String skillIconName;
        private List<CombatSkillData> combatSkillDataList;
    }

    @Data
    public static class PassiveSkill {
        private Integer id;
        private String name;
        private String desc;
        private String descExtra;
        private String loreDesc;
        private String skillIconName;
    }

    @Data
    public static class CombatSkillData {
        private Integer level;
        private List<String> paramDescList;
        private List<Double> paramList;
    }

    @Data
    public static class SpecialProudSkill {
        private Integer id;
        private String name;
        private String desc;
        private String descExtra;
        private String loreDesc;
        private String skillIconName;
    }
}
