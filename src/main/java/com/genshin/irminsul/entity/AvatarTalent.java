package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_talent")
public class AvatarTalent {
    @Id
    private Integer id;

    // 名称、描述与完整描述的文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("descExtraTextMapHash")
    private String descExtraTextMapHash;

    // 图标与数值参数
    @Field("icon")
    private String icon;

    @Field("paramList")
    private List<Double> paramList;

    // 命座提供的属性加成
    @Field("addProps")
    private List<AddProp> addProps;

    // 解锁条件配置
    @Field("openConfig")
    private String openConfig;

    // 单条属性加成
    @Data
    public static class AddProp {
        @Field("propType")
        private String propType;

        @Field("value")
        private Double value;
    }
}
