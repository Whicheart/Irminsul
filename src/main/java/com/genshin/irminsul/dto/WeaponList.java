package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class WeaponList {

    private List<WeaponBrief> weapons;

    @Data
    public static class WeaponBrief {

        private Integer id;

        // 排序
        private Integer sortId;

        // 筛选条件
        private String name;
        private Integer rank;
        private String weaponType;

        // 图像资源
        private String GachaCardName;
        private String iconName;
    }
}
