package com.genshin.lunar.dto;

import lombok.Data;

import java.util.List;

@Data
public class AvatarSkillDTO {
    private Integer id;
    private List<CombatSkill> combatSkills;
    private List<PassiveSkill> passiveSkills;

    @Data
    public static class CombatSkill {
        private Integer id;
        private String name;
        private String desc;
        private String extraDesc;
        private String descExtra;
        private String icon;
        private List<CombatSkillData> combatSkillDataList;
    }

    @Data
    public static class PassiveSkill {
        private Integer id;
        private String name;
        private String desc;
        private String descExtra;
        private String loreDesc;
    }

    @Data
    public static class CombatSkillData {
        private Integer level;
        private List<String> paramDescList;
        private List<Double> paramList;
    }
}
