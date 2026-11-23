package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 套装效果配置 */
@Data
@Document(collection = "equip_affix")
public class EquipAffix {

    // 套装效果 ID
    @Id
    private Integer id;

    // 各档效果
    @Field("levels")
    private List<AffixLevel> levels;

    // 单档套装效果
    @Data
    public static class AffixLevel {
        // 档位
        @Field("level")
        private Integer level;

        // 效果 ID
        @Field("affixId")
        private Integer affixId;

        // 名称文本哈希
        @Field("nameTextMapHash")
        private String nameTextMapHash;

        // 描述文本哈希
        @Field("descTextMapHash")
        private String descTextMapHash;

        // 解锁条件配置
        @Field("openConfig")
        private String openConfig;

        // 效果参数
        @Field("paramList")
        private List<Double> paramList;

        // 属性加成
        @Field("addProps")
        private List<AddProp> addProps;
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
