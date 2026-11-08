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

    @Field("maxChargeNum")
    private Integer maxChargeNum;

    @Field("lockShape")
    private String lockShape;

    @Field("lockWeightParams")
    private List<Double> lockWeightParams;

    @Field("isAttackCameraLock")
    private Boolean isAttackCameraLock;

    @Field("triggerID")
    private Integer triggerID;

    @Field("dragType")
    private String dragType;

    @Field("forceCanDoSkill")
    private Boolean forceCanDoSkill;

    @Field("showIconArrow")
    private Boolean showIconArrow;

    @Field("ignoreCDMinusRatio")
    private Boolean ignoreCDMinusRatio;

    @Field("isRanged")
    private Boolean isRanged;

    @Field("buffIcon")
    private String buffIcon;

    @Field("needMonitor")
    private String needMonitor;

    @Field("needStore")
    private Boolean needStore;

    @Field("isBobk")
    private Boolean isBobk;

    @Field("cdSlot")
    private Integer cdSlot;

    @Field("isLece")
    private Boolean isLece;

    @Field("globalValueKey")
    private String globalValueKey;
}
