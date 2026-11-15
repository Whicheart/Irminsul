package com.genshin.irminsul.entity.monster;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster")
public class Monster {

    @Id
    private Integer id;

    @Field("monsterName")
    private String monsterName;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("describeId")
    private Integer describeId;

    @Field("type")
    private String type;

    @Field("campID")
    private Integer campID;

    @Field("playType")
    private String playType;

    @Field("hpBase")
    private Double hpBase;

    @Field("attackBase")
    private Double attackBase;

    @Field("defenseBase")
    private Double defenseBase;

    @Field("fireSubHurt")
    private Double fireSubHurt;

    @Field("waterSubHurt")
    private Double waterSubHurt;

    @Field("iceSubHurt")
    private Double iceSubHurt;

    @Field("elecSubHurt")
    private Double elecSubHurt;

    @Field("windSubHurt")
    private Double windSubHurt;

    @Field("rockSubHurt")
    private Double rockSubHurt;

    @Field("grassSubHurt")
    private Double grassSubHurt;

    @Field("physicalSubHurt")
    private Double physicalSubHurt;

    @Field("propGrowCurves")
    private List<PropGrowCurve> propGrowCurves;

    @Field("killDropId")
    private Integer killDropId;

    @Field("hpDrops")
    private List<HpDrop> hpDrops;

    @Field("isSceneReward")
    private Boolean isSceneReward;

    @Field("affix")
    private List<Integer> affix;

    @Field("arkheType")
    private String arkheType;

    @Field("equips")
    private List<Integer> equips;

    @Field("mpPropID")
    private Integer mpPropID;

    @Field("visionLevel")
    private String visionLevel;

    @Field("securityLevel")
    private String securityLevel;

    @Field("excludeWeathers")
    private String excludeWeathers;

    @Field("radarHintID")
    private Integer radarHintID;

    @Field("featureTagGroupID")
    private Integer featureTagGroupID;

    @Field("entityBudgetLevel")
    private Integer entityBudgetLevel;

    @Field("combatBGMLevel")
    private Integer combatBGMLevel;

    @Field("ai")
    private String ai;

    @Field("lodPatternName")
    private String lodPatternName;

    @Field("serverScript")
    private String serverScript;

    @Field("skin")
    private String skin;

    @Field("isAIHashCheck")
    private Boolean isAIHashCheck;

    @Field("isInvisibleReset")
    private Boolean isInvisibleReset;

    @Field("safetyCheck")
    private Boolean safetyCheck;

    @Field("hideNameInElementView")
    private Boolean hideNameInElementView;

    @Data
    public static class HpDrop {

        @Field("dropId")
        private Integer dropId;

        @Field("hpPercent")
        private Double hpPercent;
    }

    @Data
    public static class PropGrowCurve {

        @Field("growCurve")
        private String growCurve;

        @Field("type")
        private String type;
    }
}
