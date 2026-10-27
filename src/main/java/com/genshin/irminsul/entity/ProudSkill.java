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

    @Field("proudSkillType")
    private Integer proudSkillType;

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

    /*
    // === 不知道有啥用的字段 ===
    @Field("unlockDescTextMapHash")
    private String unlockDescTextMapHash;
    @Field("breakLevel")
    private Integer breakLevel;
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

    // === 不知道是啥东西的混淆字段 ===
    @Field("CMILJGJDNAC")
    private String CMILJGJDNAC;
    @Field("KHENPLCCPDP")
    private List<Integer> KHENPLCCPDP;
    @Field("ACFMCHPBDMG")
    private List<Integer> ACFMCHPBDMG;
    @Field("INIMKGCALEE")
    private Integer INIMKGCALEE;
     */
}
