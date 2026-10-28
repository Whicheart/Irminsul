package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "weapon")
public class Weapon {
    @Id
    private Integer id;

    // 名称与描述的文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    // 星级、排序权重与武器类型
    @Field("rankLevel")
    private Integer rankLevel;

    @Field("weaponType")
    private String weaponType;

    // 基础属性、突破属性、武器特效
    @Field("weaponProp")
    private List<WeaponProp> weaponProp;

    @Field("weaponPromoteId")
    private Integer weaponPromoteId;

    @Field("skillAffix")
    private List<Integer> skillAffix;

    // 其他
    @Field("storyId")
    private Integer storyId;

    @Field("weaponBaseExp")
    private Integer weaponBaseExp;

    // 图标、立绘与贴图资源名
    @Field("icon")
    private String icon;

    @Field("awakenIcon")
    private String awakenIcon;

    // 精炼消耗、突破材料与销毁返还
    @Field("awakenCosts")
    private List<Integer> awakenCosts;

    @Field("awakenMaterial")
    private Integer awakenMaterial;

    @Data
    public static class WeaponProp {
        @Field("type")
        private String type;

        @Field("initValue")
        private Double initValue;

        @Field("propType")
        private String propType;
    }

    /*
    // === 未知字段（混淆） ===
    @Field("BHPHNALIMAG")
    private Integer BHPHNALIMAG;
    // === 感觉用不到 ===
    @Field("itemType")
    private String itemType;
    @Field("awakenTexture")
    private String awakenTexture;
    @Field("awakenLightMapTexture")
    private String awakenLightMapTexture;
    @Field("gachaCardNameHash")
    private String gachaCardNameHash;
    @Field("destroyRule")
    private String destroyRule;
    @Field("destroyReturnMaterial")
    private List<Integer> destroyReturnMaterial;
    @Field("destroyReturnMaterialCount")
    private List<Integer> destroyReturnMaterialCount;
    @Field("rank")
    private Integer rank;
    @Field("gadgetId")
    private Integer gadgetId;
    @Field("globalItemLimit")
    private Integer globalItemLimit;
    @Field("initialLockState")
    private Integer initialLockState;
    @Field("unRotate")
    private Boolean unRotate;
    @Field("weight")
    private Integer weight;
    */
}
