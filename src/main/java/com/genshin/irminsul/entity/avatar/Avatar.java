package com.genshin.irminsul.entity.avatar;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色配置 */
@Data
@Document(collection = "avatar")
public class Avatar {

    // 角色 ID
    @Id
    private Integer id;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 图鉴描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 详情描述文本哈希
    @Field("infoDescTextMapHash")
    private String infoDescTextMapHash;

    // 基础生命值
    @Field("hpBase")
    private Double hpBase;

    // 基础攻击力
    @Field("attackBase")
    private Double attackBase;

    // 基础防御力
    @Field("defenseBase")
    private Double defenseBase;

    // 元素充能效率
    @Field("chargeEfficiency")
    private Double chargeEfficiency;

    // 元素精通
    @Field("elementMastery")
    private Double elementMastery;

    // 暴击率
    @Field("critical")
    private Double critical;

    // 暴击伤害
    @Field("criticalHurt")
    private Double criticalHurt;

    // 体力恢复速度
    @Field("staminaRecoverSpeed")
    private Double staminaRecoverSpeed;

    // 属性成长曲线配置
    @Field("propGrowCurves")
    private List<PropGrowCurve> propGrowCurves;

    // 初始武器 ID
    @Field("initialWeapon")
    private Integer initialWeapon;

    // 特性标签组 ID
    @Field("featureTagGroupID")
    private Integer featureTagGroupID;

    // 技能组 ID
    @Field("skillDepotId")
    private Integer skillDepotId;

    // 候选技能组 ID
    @Field("candSkillDepotIds")
    private List<Integer> candSkillDepotIds;

    // 突破配置 ID
    @Field("avatarPromoteId")
    private Integer avatarPromoteId;

    // 突破奖励 ID
    @Field("avatarPromoteRewardIdList")
    private List<Integer> avatarPromoteRewardIdList;

    // 突破奖励等级
    @Field("avatarPromoteRewardLevelList")
    private List<Integer> avatarPromoteRewardLevelList;

    // 武器类型
    @Field("weaponType")
    private String weaponType;

    // 品质
    @Field("qualityType")
    private String qualityType;

    // 体型
    @Field("bodyType")
    private String bodyType;

    // 使用类型
    @Field("useType")
    private String useType;

    // 身份类型
    @Field("avatarIdentityType")
    private String avatarIdentityType;

    // 是否远程攻击
    @Field("isRangeAttack")
    private Boolean isRangeAttack;

    // 特性标签
    @Field("tags")
    private List<String> tags;

    // 立绘名
    @Field("imageName")
    private String imageName;

    // 侧边图标名
    @Field("sideIconName")
    private String sideIconName;

    // 图标名
    @Field("iconName")
    private String iconName;

    // 属性成长曲线
    @Data
    public static class PropGrowCurve {

        // 曲线名
        @Field("growCurve")
        private String growCurve;

        // 属性类型
        @Field("type")
        private String type;
    }
}
