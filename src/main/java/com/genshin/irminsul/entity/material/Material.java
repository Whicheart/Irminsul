package com.genshin.irminsul.entity.material;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 材料配置 */
@Data
@Document(collection = "material")
public class Material {

    // 材料 ID
    @Id
    private Integer id;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 类型文本哈希
    @Field("typeDescTextMapHash")
    private String typeDescTextMapHash;

    // 特殊描述文本哈希
    @Field("specialDescTextMapHash")
    private String specialDescTextMapHash;

    // 效果描述文本哈希
    @Field("effectDescTextMapHash")
    private String effectDescTextMapHash;

    // 交互标题文本哈希
    @Field("interactionTitleTextMapHash")
    private String interactionTitleTextMapHash;

    // 道具类型
    @Field("itemType")
    private String itemType;

    // 材料类型
    @Field("materialType")
    private String materialType;

    // 排序等级
    @Field("rank")
    private Integer rank;

    // 星级
    @Field("rankLevel")
    private Integer rankLevel;

    // 食物品质
    @Field("foodQuality")
    private String foodQuality;

    // 筛选类型
    @Field("filterType")
    private String filterType;

    // 使用效果
    @Field("itemUse")
    private List<ItemUse> itemUse;

    // 效果名
    @Field("effectName")
    private String effectName;

    // 效果类型
    @Field("effectType")
    private String effectType;

    // 效果图标名
    @Field("effectIcon")
    private String effectIcon;

    // 使用目标
    @Field("useTarget")
    private String useTarget;

    // 使用等级
    @Field("useLevel")
    private Integer useLevel;

    // 最大使用次数
    @Field("maxUseCount")
    private Integer maxUseCount;

    // 饱食度参数
    @Field("satiationParams")
    private List<Integer> satiationParams;

    // 堆叠上限
    @Field("stackLimit")
    private Integer stackLimit;

    // 全局持有上限
    @Field("globalItemLimit")
    private Integer globalItemLimit;

    // 重量
    @Field("weight")
    private Integer weight;

    // 冷却时间
    @Field("cdTime")
    private Integer cdTime;

    // 冷却组
    @Field("cdGroup")
    private Integer cdGroup;

    // 销毁规则
    @Field("destroyRule")
    private String destroyRule;

    // 图标名
    @Field("icon")
    private String icon;

    // 图片路径
    @Field("picPath")
    private List<String> picPath;

    // 关联道具 ID
    @Field("gadgetId")
    private Integer gadgetId;

    // 效果道具 ID
    @Field("effectGadgetID")
    private Integer effectGadgetID;

    // 套装 ID
    @Field("setID")
    private Integer setID;

    // 使用效果条目
    @Data
    public static class ItemUse {

        // 使用参数
        @Field("useParam")
        private List<String> useParam;

        // 使用操作
        @Field("useOp")
        private String useOp;
    }
}
