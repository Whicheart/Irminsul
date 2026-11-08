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

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("equipType")
    private String equipType;

    @Field("itemType")
    private String itemType;

    @Field("rank")
    private Integer rank;

    @Field("rankLevel")
    private Integer rankLevel;

    @Field("maxLevel")
    private Integer maxLevel;

    @Field("weight")
    private Integer weight;

    @Field("setId")
    private Integer setId;

    @Field("storyId")
    private Integer storyId;

    @Field("mainPropDepotId")
    private Integer mainPropDepotId;

    @Field("appendPropDepotId")
    private Integer appendPropDepotId;

    @Field("appendPropNum")
    private Integer appendPropNum;

    @Field("addPropLevels")
    private List<Integer> addPropLevels;

    @Field("baseConvExp")
    private Integer baseConvExp;

    @Field("icon")
    private String icon;

    @Field("showPic")
    private String showPic;

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
}
