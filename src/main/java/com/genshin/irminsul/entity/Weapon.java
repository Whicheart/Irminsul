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

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("weaponType")
    private String weaponType;

    @Field("itemType")
    private String itemType;

    @Field("rank")
    private Integer rank;

    @Field("rankLevel")
    private Integer rankLevel;

    @Field("weaponBaseExp")
    private Integer weaponBaseExp;

    @Field("weaponProp")
    private List<WeaponProp> weaponProp;

    @Field("weaponPromoteId")
    private Integer weaponPromoteId;

    @Field("skillAffix")
    private List<Integer> skillAffix;

    @Field("storyId")
    private Integer storyId;

    @Field("enhanceRule")
    private Integer enhanceRule;

    @Field("globalItemLimit")
    private Integer globalItemLimit;

    @Field("icon")
    private String icon;

    @Field("awakenIcon")
    private String awakenIcon;

    @Field("awakenTexture")
    private String awakenTexture;

    @Field("awakenLightMapTexture")
    private String awakenLightMapTexture;

    @Field("awakenMaterial")
    private Integer awakenMaterial;

    @Field("awakenCosts")
    private List<Integer> awakenCosts;

    @Field("destroyRule")
    private String destroyRule;

    @Field("destroyReturnMaterial")
    private List<Integer> destroyReturnMaterial;

    @Field("destroyReturnMaterialCount")
    private List<Integer> destroyReturnMaterialCount;

    @Field("gadgetId")
    private Integer gadgetId;

    @Field("initialLockState")
    private Integer initialLockState;

    @Field("unRotate")
    private Boolean unRotate;

    @Field("weight")
    private Integer weight;

    @Data
    public static class WeaponProp {

        @Field("type")
        private String type;

        @Field("initValue")
        private Double initValue;

        @Field("propType")
        private String propType;
    }
}
