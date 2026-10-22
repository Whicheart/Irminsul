package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "material_codex")
public class MaterialCodex {
    @Id
    private Integer id;

    // 对应的物品 ID
    @Field("materialId")
    private Integer materialId;

    // 名称与描述的文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 排序与类型
    @Field("sortOrder")
    private Integer sortOrder;
    @Field("type")
    private String type;

    // 任务替换用的名称与描述
    @Field("questReplaceName1TextMapHash")
    private String questReplaceName1TextMapHash;
    @Field("questReplaceDesc1TextMapHash")
    private String questReplaceDesc1TextMapHash;
    @Field("questReplaceName2TextMapHash")
    private String questReplaceName2TextMapHash;
    @Field("questReplaceDesc2TextMapHash")
    private String questReplaceDesc2TextMapHash;
}
