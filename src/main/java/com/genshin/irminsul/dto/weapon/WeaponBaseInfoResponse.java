package com.genshin.irminsul.dto.weapon;

import java.util.List;
import lombok.Data;

/** 武器详情 */
@Data
public class WeaponBaseInfoResponse {

    // 武器 ID
    private Integer id;

    // 名称
    private String name;
    // 描述
    private String desc;
    // 武器类型
    private String weaponType;
    // 星级
    private Integer rankLevel;
    // 图标名
    private String icon;
    // 突破后图标名
    private String awakenIcon;

    // 1~100 级攻击力
    private List<Double> atk;
    // 副属性名
    private String subStatName;
    // 副属性各等级数值
    private List<Double> subStatValue;
    // 精炼技能名
    private String affixName;
    // 各精炼等级描述
    private List<String> affixDesc;

    // 突破材料
    private List<Material> materials;
    // 武器故事
    private List<List<String>> stories;

    // 单次突破材料
    @Data
    public static class Material {

        // 突破等级
        private Integer promoteLevel;
        // 解锁等级上限
        private Integer maxLevel;
        // 摩拉消耗
        private Integer mora;
        // 材料条目
        private List<CostItem> costItems;
    }

    // 材料条目
    @Data
    public static class CostItem {

        // 材料 ID
        private Integer id;
        // 名称
        private String name;
        // 数量
        private Integer count;
        // 图标名
        private String iconName;
    }
}
