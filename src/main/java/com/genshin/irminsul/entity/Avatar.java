package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar")
public class Avatar {

    @Id
    private Integer id;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("infoDescTextMapHash")
    private String infoDescTextMapHash;

    @Field("hpBase")
    private Double hpBase;

    @Field("attackBase")
    private Double attackBase;

    @Field("defenseBase")
    private Double defenseBase;

    @Field("chargeEfficiency")
    private Double chargeEfficiency;

    @Field("elementMastery")
    private Double elementMastery;

    @Field("critical")
    private Double critical;

    @Field("criticalHurt")
    private Double criticalHurt;

    @Field("staminaRecoverSpeed")
    private Double staminaRecoverSpeed;

    @Field("propGrowCurves")
    private List<PropGrowCurve> propGrowCurves;

    @Field("initialWeapon")
    private Integer initialWeapon;

    @Field("manekinMotionConfig")
    private Integer manekinMotionConfig;

    @Field("featureTagGroupID")
    private Integer featureTagGroupID;

    @Field("skillDepotId")
    private Integer skillDepotId;

    @Field("candSkillDepotIds")
    private List<Integer> candSkillDepotIds;

    @Field("avatarPromoteId")
    private Integer avatarPromoteId;

    @Field("avatarPromoteRewardIdList")
    private List<Integer> avatarPromoteRewardIdList;

    @Field("avatarPromoteRewardLevelList")
    private List<Integer> avatarPromoteRewardLevelList;

    @Field("weaponType")
    private String weaponType;

    @Field("qualityType")
    private String qualityType;

    @Field("bodyType")
    private String bodyType;

    @Field("useType")
    private String useType;

    @Field("avatarIdentityType")
    private String avatarIdentityType;

    @Field("isRangeAttack")
    private Boolean isRangeAttack;

    @Field("tags")
    private List<String> tags;

    @Field("imageName")
    private String imageName;

    @Field("sideIconName")
    private String sideIconName;

    @Field("iconName")
    private String iconName;

    @Data
    public static class PropGrowCurve {

        @Field("growCurve")
        private String growCurve;

        @Field("type")
        private String type;
    }
}
