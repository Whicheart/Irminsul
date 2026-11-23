package com.genshin.irminsul.dto.weapon;

import java.util.List;
import lombok.Data;

/** 武器列表响应 */
@Data
public class WeaponList {

    // 简要武器列表
    private List<WeaponBrief> weapons;

    // 列表项
    @Data
    public static class WeaponBrief {

        // 武器 ID
        private Integer id;

        // 图鉴排序号
        private Integer sortId;

        // 名称
        private String name;
        // 星级
        private Integer rank;
        // 武器类型
        private String weaponType;

        // 抽卡卡面名
        private String GachaCardName;
        // 图标名
        private String iconName;
    }
}
