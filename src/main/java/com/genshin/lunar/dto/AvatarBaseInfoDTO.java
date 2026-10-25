package com.genshin.lunar.dto;

import lombok.Data;

import java.util.List;

@Data
public class AvatarBaseInfoDTO {
    private Integer id;
    private String name;
    private String title;
    private String desc;
    private String qualityType;
    private String weaponType;
    private String releaseTime;
    private List<String> affiliation;

    private Birth birth;
    private Constellation constellation;
    private Power power;
    private Element element;
    private Cv cv;

    private List<Double> hp;
    private List<Double> atk;
    private List<Double> def;
    private Double elementMastery;
    private String ascensionStatName;
    private List<Double> ascensionStatValue;

    private List<Material> materials;

    private String splashIconName;

    @Data
    public static class Birth {
        private Integer month;
        private Integer day;
    }

    @Data
    public static class Constellation {
        private String befor;
        private String after;
    }

    @Data
    public static class Power {
        private String befor;
        private String after;
    }

    @Data
    public static class Element {
        private String befor;
        private String after;
    }

    @Data
    public static class Cv {
        private String cn;
        private String en;
        private String jp;
        private String kr;
    }

    @Data
    public static class Material {
        private List<LevelUpMaterial> levelUpMaterial;
        private List<TalentUpMaterial> talentUpMaterial;
    }

    @Data
    public static class LevelUpMaterial {
        private Integer promoteLevel;
        private Integer maxLevel;
        private Integer mora;
        private List<CostItem> costItems;
    }

    @Data
    public static class TalentUpMaterial {
        private Integer level;
        private Integer mora;
        private List<CostItem> costItems;
    }

    @Data
    public static class CostItem {
        private Integer id;
        private String name;
        private Integer count;
    }
}
