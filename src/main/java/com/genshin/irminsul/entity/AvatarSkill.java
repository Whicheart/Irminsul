package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_skill")
public class AvatarSkill {
    @Id
    private Integer id;

    @Field("proudSkillGroupId")
    private Integer proudSkillGroupId;

    @Field("abilityName")
    private String abilityName;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("extraDescTextMapHash")
    private String extraDescTextMapHash;

    @Field("descExtraTextMapHash")
    private String descExtraTextMapHash;

    @Field("skillIcon")
    private String skillIcon;

    @Field("cdTime")
    private Double cdTime;

    @Field("costElemType")
    private String costElemType;

    @Field("costElemVal")
    private Double costElemVal;

    @Field("costStamina")
    private Double costStamina;

    @Field("specialEnergyType")
    private String specialEnergyType;

    @Field("energyMin")
    private Double energyMin;

    @Field("specialEnergyMin")
    private Double specialEnergyMin;

    @Field("specialEnergyMax")
    private Double specialEnergyMax;

    @Field("lockShape")
    private String lockShape;

    @Field("lockWeightParams")
    private List<Double> lockWeightParams;

    @Field("isAttackCameraLock")
    private Boolean isAttackCameraLock;

    /*
    // === 不一定有用 和 不知道用来干嘛 和 或许有用 ===
    @Field("maxChargeNum")
    private Integer maxChargeNum;
    @Field("buffIcon")
    private String buffIcon;
    @Field("shareCDID")
    private Integer shareCDID;
    @Field("cdSlot")
    private Integer cdSlot;
    @Field("globalValueKey")
    private String globalValueKey;
    @Field("triggerID")
    private Integer triggerID;
    @Field("dragType")
    private String dragType;
    @Field("needMonitor")
    private String needMonitor;

    // === 一堆不知道是什么玩意的混淆字段 ===

    @Field("BGMLPHCEHOL")
    private String BGMLPHCEHOL;

    // === 一堆不知道是什么玩意的混淆布尔字段 ===
    @Field("AILNLGPMDIL")
    private Boolean AILNLGPMDIL;
    @Field("MKBDMKMFLPN")
    private Boolean MKBDMKMFLPN;
    @Field("KOMGFAGBDFJ")
    private Boolean KOMGFAGBDFJ;
    @Field("MNHDMLMIBNJ")
    private Boolean MNHDMLMIBNJ;
    @Field("JPHOAKOCJNP")
    private Boolean JPHOAKOCJNP;
    @Field("CHFJHOOMPKM")
    private Boolean CHFJHOOMPKM;
    @Field("NIFEPGKKMAL")
    private Boolean NIFEPGKKMAL;
    @Field("IEBGELFLKJP")
    private Boolean IEBGELFLKJP;
    @Field("BKOGECMNPJP")
    private Boolean BKOGECMNPJP;
    @Field("NHINPABHLGM")
    private Boolean NHINPABHLGM;
     */
}
