package com.genshin.irminsul.entity.reliquary;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 圣遗物部件配置 */
@Data
@Document(collection = "reliquary")
public class Reliquary {

    // 部件 ID
    @Id
    private Integer id;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 部位类型
    @Field("equipType")
    private String equipType;

    // 道具类型
    @Field("itemType")
    private String itemType;

    // 排序等级
    @Field("rank")
    private Integer rank;

    // 星级
    @Field("rankLevel")
    private Integer rankLevel;

    // 最大等级
    @Field("maxLevel")
    private Integer maxLevel;

    // 重量
    @Field("weight")
    private Integer weight;

    // 所属套装 ID
    @Field("setId")
    private Integer setId;

    // 故事 ID
    @Field("storyId")
    private Integer storyId;

    // 主属性库 ID
    @Field("mainPropDepotId")
    private Integer mainPropDepotId;

    // 副词条库 ID
    @Field("appendPropDepotId")
    private Integer appendPropDepotId;

    // 副词条数量
    @Field("appendPropNum")
    private Integer appendPropNum;

    // 属性强化等级
    @Field("addPropLevels")
    private List<Integer> addPropLevels;

    // 基础转化经验
    @Field("baseConvExp")
    private Integer baseConvExp;

    // 图标名
    @Field("icon")
    private String icon;

    // 展示图片名
    @Field("showPic")
    private String showPic;

    // 关联道具 ID
    @Field("gadgetId")
    private Integer gadgetId;

    // 销毁规则
    @Field("destroyRule")
    private String destroyRule;

    // 销毁返还材料
    @Field("destroyReturnMaterial")
    private List<Integer> destroyReturnMaterial;

    // 销毁返还材料数量
    @Field("destroyReturnMaterialCount")
    private List<Integer> destroyReturnMaterialCount;
}
