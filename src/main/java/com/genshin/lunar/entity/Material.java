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

    // 名称、描述、类型说明、特殊说明与效果说明的文本哈希
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

    // 图标、类型与堆叠上限
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

    // 背包筛选、食物品质与套装
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

    // 使用次数、使用目标与冷却
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
