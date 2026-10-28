package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class WeaponBaseInfoDTO {
    private Integer id;

    private String name;
    private String desc;
    private String weaponType;
    private Integer rankLevel;
    private String icon;
    private String awakenIcon;

    private List<Double> atk;
    private String subStatName;
    private List<Double> subStatValue;
    private String affixName;
    private List<String> affixDesc;

    private List<Material> materials;
    private List<Story> stories;

    @Data
    public static class Story {
        private List<String> story;
    }

    @Data
    public static class Material {
        private Integer promoteLevel;
        private Integer maxLevel;
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
