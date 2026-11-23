package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

/** 角色基础信息 */
@Data
public class AvatarBaseInfoDTO {

    // 角色 ID
    private Integer id;

    // 称号
    private String title;
    // 名称
    private String name;
    // 简介
    private String desc;
    // 生日
    private Birth birth;
    // 命座名（变更前/后）
    private Constellation constellation;

    // 所属国家
    private String country;
    // 所属组织（变更前/后）
    private List<String> affiliation;

    // 品质
    private String qualityType;
    // 神之眼
    private Vision vision;
    // 元素
    private Element element;
    // 武器类型
    private String weaponType;
    // 特性标签
    private List<String> tags;
    // 始基力类型
    private String arkheType;

    // 1~100 级生命值
    private List<Double> hp;
    // 1~100 级攻击力
    private List<Double> atk;
    // 1~100 级防御力
    private List<Double> def;
    // 元素精通初值
    private Double elementMastery;
    // 突破属性名
    private String ascensionStatName;
    // 突破属性各阶段数值
    private List<Double> ascensionStatValue;
    // 突破与天赋材料
    private List<Material> materials;

    // 抽卡立绘名
    private String gachaImageName;

    // 实装时间
    private String releaseTime;
    // 四语言配音
    private Cv cv;

    // 生日
    @Data
    public static class Birth {

        // 月
        private Integer month;
        // 日
        private Integer day;
    }

    // 命座名
    @Data
    public static class Constellation {

        // 变更前文案
        private String befor;
        // 变更后文案
        private String after;
    }

    // 神之眼
    @Data
    public static class Vision {

        // 图标名
        private String iconName;
        // 变更前文案
        private String befor;
        // 变更后文案
        private String after;
    }

    // 元素
    @Data
    public static class Element {

        // 图标名
        private String iconName;
        // 变更前文案
        private String befor;
        // 变更后文案
        private String after;
    }

    // 培养材料分组
    @Data
    public static class Material {

        // 突破材料
        private List<LevelUpMaterial> levelUpMaterial;
        // 天赋材料
        private List<TalentUpMaterial> talentUpMaterial;
    }

    // 单次突破材料
    @Data
    public static class LevelUpMaterial {

        // 突破等级
        private Integer promoteLevel;
        // 解锁等级上限
        private Integer unlockMaxLevel;
        // 摩拉消耗
        private Integer moraCost;
        // 材料条目
        private List<CostItem> costItems;
    }

    // 单级天赋材料
    @Data
    public static class TalentUpMaterial {

        // 天赋等级
        private Integer level;
        // 摩拉消耗
        private Integer moraCost;
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

    // 配音演员
    @Data
    public static class Cv {

        // 中配
        private String cn;
        // 英配
        private String en;
        // 日配
        private String jp;
        // 韩配
        private String kr;
    }
}
