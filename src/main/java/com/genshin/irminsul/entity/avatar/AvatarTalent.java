package com.genshin.irminsul.entity.avatar;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色命座配置 */
@Data
@Document(collection = "avatar_talent")
public class AvatarTalent {

    // 命座 ID
    @Id
    private Integer id;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 加强描述文本哈希
    @Field("descExtraTextMapHash")
    private String descExtraTextMapHash;

    // 图标名
    @Field("icon")
    private String icon;

    // 参数数值
    @Field("paramList")
    private List<Double> paramList;

    // 属性加成
    @Field("addProps")
    private List<AddProp> addProps;

    // 主要材料 ID
    @Field("mainCostItemId")
    private Integer mainCostItemId;

    // 主要材料数量
    @Field("mainCostItemCount")
    private Integer mainCostItemCount;

    // 解锁条件配置
    @Field("openConfig")
    private String openConfig;

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
