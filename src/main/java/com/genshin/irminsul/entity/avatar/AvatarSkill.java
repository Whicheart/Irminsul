package com.genshin.irminsul.entity.avatar;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色技能配置 */
@Data
@Document(collection = "avatar_skill")
public class AvatarSkill {

    // 技能 ID
    @Id
    private Integer id;

    // 天赋技能组 ID
    @Field("proudSkillGroupId")
    private Integer proudSkillGroupId;

    // 技能实现名
    @Field("abilityName")
    private String abilityName;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 技能旁白文本哈希
    @Field("extraDescTextMapHash")
    private String extraDescTextMapHash;

    // 加强描述文本哈希
    @Field("descExtraTextMapHash")
    private String descExtraTextMapHash;

    // 技能图标名
    @Field("skillIcon")
    private String skillIcon;

    // 冷却时间（秒）
    @Field("cdTime")
    private Double cdTime;

    // 消耗元素类型
    @Field("costElemType")
    private String costElemType;

    // 消耗元素值
    @Field("costElemVal")
    private Double costElemVal;

    // 消耗体力
    @Field("costStamina")
    private Double costStamina;

    // 特殊能量类型
    @Field("specialEnergyType")
    private String specialEnergyType;

    // 元素能量下限
    @Field("energyMin")
    private Double energyMin;

    // 特殊能量下限
    @Field("specialEnergyMin")
    private Double specialEnergyMin;

    // 特殊能量上限
    @Field("specialEnergyMax")
    private Double specialEnergyMax;

    // 最大蓄力次数
    @Field("maxChargeNum")
    private Integer maxChargeNum;

    // 索敌区间
    @Field("lockShape")
    private String lockShape;

    // 索敌权重参数
    @Field("lockWeightParams")
    private List<Double> lockWeightParams;

    // 触发器 ID
    @Field("triggerID")
    private Integer triggerID;

    // 是否强制可释放
    @Field("forceCanDoSkill")
    private Boolean forceCanDoSkill;

    // 是否忽略冷却缩减
    @Field("ignoreCDMinusRatio")
    private Boolean ignoreCDMinusRatio;

    // 是否远程技能
    @Field("isRanged")
    private Boolean isRanged;

    // 增益图标名
    @Field("buffIcon")
    private String buffIcon;

    // 监控配置
    @Field("needMonitor")
    private String needMonitor;

    // 是否需要存储
    @Field("needStore")
    private Boolean needStore;

    // 冷却槽位
    @Field("cdSlot")
    private Integer cdSlot;

    // 全局数值键
    @Field("globalValueKey")
    private String globalValueKey;
}
