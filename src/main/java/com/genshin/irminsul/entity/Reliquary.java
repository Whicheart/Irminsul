package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary")
public class Reliquary {
    @Id
    private Integer id;

    // 名称与描述的文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    // 星级与部位（EQUIP_FLOWER、EQUIP_NECKLACE 等）
    @Field("rankLevel")
    private Integer rankLevel;

    @Field("equipType")
    private String equipType;

    @Field("maxLevel")
    private Integer maxLevel;

    // 图标资源名
    @Field("icon")
    private String icon;

    // 主词条、副词条所在的仓库
    @Field("mainPropDepotId")
    private Integer mainPropDepotId;

    @Field("appendPropDepotId")
    private Integer appendPropDepotId;

    @Field("appendPropNum")
    private Integer appendPropNum;

    @Field("addPropLevels")
    private List<Integer> addPropLevels;

    // 所属套装与故事
    @Field("setId")
    private Integer setId;

    @Field("storyId")
    private Integer storyId;

    /*
    // === 背包/养成相关，暂未使用 ===
    @Field("baseConvExp")
    private Integer baseConvExp;
    @Field("rank")
    private Integer rank;
    @Field("weight")
    private Integer weight;
    @Field("itemType")
    private String itemType;
    @Field("gadgetId")
    private Integer gadgetId;
    @Field("dropable")
    private Boolean dropable;
    @Field("destroyRule")
    private String destroyRule;
    @Field("destroyReturnMaterial")
    private List<Integer> destroyReturnMaterial;
    @Field("destroyReturnMaterialCount")
    private List<Integer> destroyReturnMaterialCount;
    @Field("showPic")
    private String showPic;
     */
}
