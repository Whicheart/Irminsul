package com.genshin.irminsul.entity.weapon;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 武器配置 */
@Data
@Document(collection = "weapon")
public class Weapon {

    // 武器 ID
    @Id
    private Integer id;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 武器类型
    @Field("weaponType")
    private String weaponType;

    // 道具类型
    @Field("itemType")
    private String itemType;

    // 排序等级
    @Field("rank")
    private Integer rank;

    // 星级
    @Field("rankLevel")
    private Integer rankLevel;

    // 基础经验
    @Field("weaponBaseExp")
    private Integer weaponBaseExp;

    // 基础属性
    @Field("weaponProp")
    private List<WeaponProp> weaponProp;

    // 突破配置 ID
    @Field("weaponPromoteId")
    private Integer weaponPromoteId;

    // 技能效果 ID 列表
    @Field("skillAffix")
    private List<Integer> skillAffix;

    // 故事 ID
    @Field("storyId")
    private Integer storyId;

    // 强化规则
    @Field("enhanceRule")
    private Integer enhanceRule;

    // 全局持有上限
    @Field("globalItemLimit")
    private Integer globalItemLimit;

    // 图标名
    @Field("icon")
    private String icon;

    // 突破后图标名
    @Field("awakenIcon")
    private String awakenIcon;

    // 突破材料
    @Field("awakenMaterial")
    private Integer awakenMaterial;

    // 突破消耗
    @Field("awakenCosts")
    private List<Integer> awakenCosts;

    // 销毁规则
    @Field("destroyRule")
    private String destroyRule;

    // 销毁返还材料
    @Field("destroyReturnMaterial")
    private List<Integer> destroyReturnMaterial;

    // 销毁返还材料数量
    @Field("destroyReturnMaterialCount")
    private List<Integer> destroyReturnMaterialCount;

    // 关联道具 ID
    @Field("gadgetId")
    private Integer gadgetId;

    // 重量
    @Field("weight")
    private Integer weight;

    // 武器基础属性
    @Data
    public static class WeaponProp {

        // 属性类型
        @Field("type")
        private String type;

        // 初始值
        @Field("initValue")
        private Double initValue;

        // 属性枚举
        @Field("propType")
        private String propType;
    }
}
