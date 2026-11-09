package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarBaseInfoDTO {

    private Integer id;

    // 基础信息
    private String title;
    private String name;
    private String desc;
    private Birth birth;
    private Constellation constellation;

    // 所属
    private String country;
    private List<String> affiliation;

    // 类型标签
    private String qualityType;
    private Vision vision;
    private Element element;
    private String weaponType;
    private List<String> tags;
    private String arkheType;

    // 基础属性与材料
    private List<Double> hp;
    private List<Double> atk;
    private List<Double> def;
    private Double elementMastery;
    private String ascensionStatName;
    private List<Double> ascensionStatValue;
    private List<Material> materials;

    // 图像资源
    private String gachaImageName;

    // 游戏外信息
    private String releaseTime;
    private Cv cv;

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
    public static class Vision {

        private String iconName;
        private String befor;
        private String after;
    }

    @Data
    public static class Element {

        private String iconName;
        private String befor;
        private String after;
    }

    @Data
    public static class Material {

        private List<LevelUpMaterial> levelUpMaterial;
        private List<TalentUpMaterial> talentUpMaterial;
    }

    @Data
    public static class LevelUpMaterial {

        private Integer promoteLevel;
        private Integer unlockMaxLevel;
        private Integer moraCost;
        private List<CostItem> costItems;
    }

    @Data
    public static class TalentUpMaterial {

        private Integer level;
        private Integer moraCost;
        private List<CostItem> costItems;
    }

    @Data
    public static class CostItem {

        private Integer id;
        private String name;
        private Integer count;
        private String iconName;
    }

    @Data
    public static class Cv {

        private String cn;
        private String en;
        private String jp;
        private String kr;
    }
}
