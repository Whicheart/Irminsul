package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "avatar")
public class Avatar {
    @Id
    private Integer id;

    @Field("nameTextMapHash")
    private String nameTextMapHash;
    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("hpBase")
    private Double hpBase;
    @Field("attackBase")
    private Double attackBase;
    @Field("defenseBase")
    private Double defenseBase;
    @Field("critical")
    private Double critical;
    @Field("criticalHurt")
    private Double criticalHurt;
    @Field("chargeEfficiency")
    private Double chargeEfficiency;
    @Field("elementMastery")
    private Double elementMastery;

    @Field("skillDepotId")
    private Integer skillDepotId;
    @Field("avatarPromoteId")
    private Integer avatarPromoteId;

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
    @Field("tags")
    private List<String> tags;
    @Field("isRangeAttack")
    private Boolean isRangeAttack;

    @Field("imageName")
    private String imageName;
    @Field("sideIconName")
    private String sideIconName;
    @Field("iconName")
    private String iconName;

    @Field("propGrowCurves")
    private List<PropGrowCurve> propGrowCurves;
    @Data
    public static class PropGrowCurve {
        @Field("growCurve")
        private String growCurve;
        @Field("type")
        private String type;
    }

    /*
    // === 某种加成属性但字段是混淆的 ===
    @Field("OPABFGCNNHB")
    private String OPABFGCNNHB; // 比如 "FIGHT_PROP_ELEMENT_MASTERY"

    @Field("JEFOBMDHIFJ")
    private String JEFOBMDHIFJ; // 比如 "FIGHT_PROP_CRITICAL"

    @Field("GOEONNEACMM")
    private String GOEONNEACMM; // 比如 "FIGHT_PROP_CRITICAL_HURT"

    // === 某种 ID 但字段是混淆的 ===
    @Field("HPOFBCBCNAE")
    private String HPOFBCBCNAE;

    @Field("CAPNJKODKKA")
    private String CAPNJKODKKA;

    // === 某种布尔但字段是混淆的 ===
    @Field("GNNKOOOLBAJ")
    private Boolean GNNKOOOLBAJ;

    @Field("AKGABJMLFMC")
    private Boolean AKGABJMLFMC;

    // === 其他明文但不确定要不要用的 ===
    @Field("gachaImageNameHash")
    private String gachaImageNameHash;

    @Field("gachaCardNameHash")
    private String gachaCardNameHash;

    @Field("featureTagGroupID")
    private Integer featureTagGroupID;

    @Field("candSkillDepotIds")
    private List<Integer> candSkillDepotIds;
    */
}
