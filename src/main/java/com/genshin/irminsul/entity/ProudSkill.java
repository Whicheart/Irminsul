package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "proud_skill")
public class ProudSkill {

    @Id
    private Integer id;

    @Field("proudSkillGroupId")
    private Integer proudSkillGroupId;

    @Field("level")
    private Integer level;

    @Field("breakLevel")
    private Integer breakLevel;

    @Field("proudSkillType")
    private Integer proudSkillType;

    @Field("displayType")
    private String displayType;

    @Field("effectiveForTeam")
    private Integer effectiveForTeam;

    @Field("icon")
    private String icon;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("descExtraTextMapHash")
    private String descExtraTextMapHash;

    @Field("loreDescTextMapHash")
    private String loreDescTextMapHash;

    @Field("unlockDescTextMapHash")
    private String unlockDescTextMapHash;

    @Field("paramDescList")
    private List<String> paramDescList;

    @Field("paramList")
    private List<Double> paramList;

    @Field("coinCost")
    private Integer coinCost;

    @Field("costItems")
    private List<CostItem> costItems;

    @Field("addProps")
    private List<AddProp> addProps;

    @Field("lifeEffectType")
    private String lifeEffectType;

    @Field("lifeEffectParams")
    private List<String> lifeEffectParams;

    @Field("isHideLifeProudSkill")
    private Boolean isHideLifeProudSkill;

    @Field("filterConds")
    private List<String> filterConds;

    @Field("openConfig")
    private String openConfig;

    @Data
    public static class CostItem {

        @Field("id")
        private Integer id;

        @Field("count")
        private Integer count;
    }

    @Data
    public static class AddProp {

        @Field("propType")
        private String propType;

        @Field("value")
        private Double value;
    }
}
