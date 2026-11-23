package com.genshin.irminsul.entity.monster;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物配置 */
@Data
@Document(collection = "monster")
public class Monster {

    // 怪物 ID
    @Id
    private Integer id;

    // 内部名
    @Field("monsterName")
    private String monsterName;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述表 ID，指向 monster_describe
    @Field("describeId")
    private Integer describeId;

    // 怪物类型
    @Field("type")
    private String type;

    // 阵营 ID
    @Field("campID")
    private Integer campID;

    // 玩法类型
    @Field("playType")
    private String playType;

    // 基础生命值
    @Field("hpBase")
    private Double hpBase;

    // 基础攻击力
    @Field("attackBase")
    private Double attackBase;

    // 基础防御力
    @Field("defenseBase")
    private Double defenseBase;

    // 火元素抗性
    @Field("fireSubHurt")
    private Double fireSubHurt;

    // 水元素抗性
    @Field("waterSubHurt")
    private Double waterSubHurt;

    // 冰元素抗性
    @Field("iceSubHurt")
    private Double iceSubHurt;

    // 雷元素抗性
    @Field("elecSubHurt")
    private Double elecSubHurt;

    // 风元素抗性
    @Field("windSubHurt")
    private Double windSubHurt;

    // 岩元素抗性
    @Field("rockSubHurt")
    private Double rockSubHurt;

    // 草元素抗性
    @Field("grassSubHurt")
    private Double grassSubHurt;

    // 物理抗性
    @Field("physicalSubHurt")
    private Double physicalSubHurt;

    // 属性成长曲线配置
    @Field("propGrowCurves")
    private List<PropGrowCurve> propGrowCurves;

    // 击杀掉落 ID
    @Field("killDropId")
    private Integer killDropId;

    // 血量掉落配置
    @Field("hpDrops")
    private List<HpDrop> hpDrops;

    // 是否场景奖励
    @Field("isSceneReward")
    private Boolean isSceneReward;

    // 词缀 ID 列表
    @Field("affix")
    private List<Integer> affix;

    // 始基力类型
    @Field("arkheType")
    private String arkheType;

    // 装备 ID 列表
    @Field("equips")
    private List<Integer> equips;

    // 联机属性 ID
    @Field("mpPropID")
    private Integer mpPropID;

    // 特性标签组 ID
    @Field("featureTagGroupID")
    private Integer featureTagGroupID;

    // AI 配置
    @Field("ai")
    private String ai;

    // 皮肤名
    @Field("skin")
    private String skin;

    // 血量掉落条目
    @Data
    public static class HpDrop {

        // 掉落 ID
        @Field("dropId")
        private Integer dropId;

        // 触发血量百分比
        @Field("hpPercent")
        private Double hpPercent;
    }

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
