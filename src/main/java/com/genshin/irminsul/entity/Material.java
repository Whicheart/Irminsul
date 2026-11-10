package com.genshin.irminsul.entity;

import java.util.List;
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

    @Field("interactionTitleTextMapHash")
    private String interactionTitleTextMapHash;

    @Field("itemType")
    private String itemType;

    @Field("materialType")
    private String materialType;

    @Field("rank")
    private Integer rank;

    @Field("rankLevel")
    private Integer rankLevel;

    @Field("foodQuality")
    private String foodQuality;

    @Field("filterType")
    private String filterType;

    @Field("itemUse")
    private List<ItemUse> itemUse;

    @Field("effectName")
    private String effectName;

    @Field("effectType")
    private String effectType;

    @Field("effectIcon")
    private String effectIcon;

    @Field("useTarget")
    private String useTarget;

    @Field("useLevel")
    private Integer useLevel;

    @Field("maxUseCount")
    private Integer maxUseCount;

    @Field("satiationParams")
    private List<Integer> satiationParams;

    @Field("useOnGain")
    private Boolean useOnGain;

    @Field("closeBagAfterUsed")
    private Boolean closeBagAfterUsed;

    @Field("stackLimit")
    private Integer stackLimit;

    @Field("globalItemLimit")
    private Integer globalItemLimit;

    @Field("weight")
    private Integer weight;

    @Field("cdTime")
    private Integer cdTime;

    @Field("cdGroup")
    private Integer cdGroup;

    @Field("destroyRule")
    private String destroyRule;

    @Field("icon")
    private String icon;

    @Field("picPath")
    private List<String> picPath;

    @Field("gadgetId")
    private Integer gadgetId;

    @Field("effectGadgetID")
    private Integer effectGadgetID;

    @Field("setID")
    private Integer setID;

    @Field("noFirstGetHint")
    private Boolean noFirstGetHint;

    @Field("isForceGetHint")
    private Boolean isForceGetHint;

    @Field("isSplitDrop")
    private Boolean isSplitDrop;

    @Field("isHidden")
    private Boolean isHidden;

    @Field("playGainEffect")
    private Boolean playGainEffect;

    @Data
    public static class ItemUse {

        @Field("useParam")
        private List<String> useParam;

        @Field("useOp")
        private String useOp;
    }
}
