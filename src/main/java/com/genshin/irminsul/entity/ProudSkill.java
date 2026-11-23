package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 天赋技能配置，同一技能组包含 1~10 级 */
@Data
@Document(collection = "proud_skill")
public class ProudSkill {

    // 天赋技能 ID
    @Id
    private Integer id;

    // 天赋技能组 ID
    @Field("proudSkillGroupId")
    private Integer proudSkillGroupId;

    // 等级
    @Field("level")
    private Integer level;

    // 解锁突破等级
    @Field("breakLevel")
    private Integer breakLevel;

    // 天赋类型
    @Field("proudSkillType")
    private Integer proudSkillType;

    // 展示类型
    @Field("displayType")
    private String displayType;

    // 是否对队伍生效
    @Field("effectiveForTeam")
    private Integer effectiveForTeam;

    // 图标名
    @Field("icon")
    private String icon;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 加强描述文本哈希
    @Field("descExtraTextMapHash")
    private String descExtraTextMapHash;

    // 补充描述文本哈希
    @Field("loreDescTextMapHash")
    private String loreDescTextMapHash;

    // 解锁描述文本哈希
    @Field("unlockDescTextMapHash")
    private String unlockDescTextMapHash;

    // 参数描述列表
    @Field("paramDescList")
    private List<String> paramDescList;

    // 参数数值
    @Field("paramList")
    private List<Double> paramList;

    // 摩拉消耗
    @Field("coinCost")
    private Integer coinCost;

    // 升级材料
    @Field("costItems")
    private List<CostItem> costItems;

    // 属性加成
    @Field("addProps")
    private List<AddProp> addProps;

    // 生活效果类型
    @Field("lifeEffectType")
    private String lifeEffectType;

    // 生活效果参数
    @Field("lifeEffectParams")
    private List<String> lifeEffectParams;

    // 筛选条件
    @Field("filterConds")
    private List<String> filterConds;

    // 解锁条件配置
    @Field("openConfig")
    private String openConfig;

    // 升级材料条目
    @Data
    public static class CostItem {

        // 材料 ID
        @Field("id")
        private Integer id;

        // 数量
        @Field("count")
        private Integer count;
    }

    // 属性加成条目
    @Data
    public static class AddProp {

        // 属性类型
        @Field("propType")
        private String propType;

        // 数值
        @Field("value")
        private Double value;
    }
}
