package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "material")
public class Material {
    @Id
    private Integer id;

    @Field("nameTextMapHash")
    private String nameTextMapHash;
    @Field("descTextMapHash")
    private String descTextMapHash;
    @Field("typeDescTextMapHash")
    private String typeDescTextMapHash;
    @Field("specialDescTextMapHash")
    private String specialDescTextMapHash;
    @Field("effectDescTextMapHash")
    private String effectDescTextMapHash;

    @Field("icon")
    private String icon;
    @Field("itemType")
    private String itemType;
    @Field("materialType")
    private String materialType;
    @Field("rank")
    private Integer rank;
    @Field("rankLevel")
    private Integer rankLevel;
    @Field("stackLimit")
    private Integer stackLimit;
    @Field("globalItemLimit")
    private Integer globalItemLimit;

    @Field("filterType")
    private String filterType;
    @Field("effectType")
    private String effectType;
    @Field("foodQuality")
    private String foodQuality;
    @Field("effectIcon")
    private String effectIcon;
    @Field("setID")
    private Integer setID;

    @Field("maxUseCount")
    private Integer maxUseCount;
    @Field("useTarget")
    private String useTarget;
    @Field("cdTime")
    private Integer cdTime;
    @Field("cdGroup")
    private Integer cdGroup;

    /*
    // === 不确定要不要用 ===
    @Field("interactionTitleTextMapHash")
    private String interactionTitleTextMapHash;
     */
}
