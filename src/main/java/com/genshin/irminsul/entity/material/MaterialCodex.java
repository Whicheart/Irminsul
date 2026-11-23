package com.genshin.irminsul.entity.material;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 材料图鉴配置 */
@Data
@Document(collection = "material_codex")
public class MaterialCodex {

    // 图鉴 ID
    @Id
    private Integer id;

    // 材料 ID
    @Field("materialId")
    private Integer materialId;

    // 图鉴类型
    @Field("type")
    private String type;

    // 排序号
    @Field("sortOrder")
    private Integer sortOrder;

    // 图标名
    @Field("icon")
    private String icon;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 任务替换名称文本哈希（一）
    @Field("questReplaceName1TextMapHash")
    private String questReplaceName1TextMapHash;

    // 任务替换描述文本哈希（一）
    @Field("questReplaceDesc1TextMapHash")
    private String questReplaceDesc1TextMapHash;

    // 任务替换名称文本哈希（二）
    @Field("questReplaceName2TextMapHash")
    private String questReplaceName2TextMapHash;

    // 任务替换描述文本哈希（二）
    @Field("questReplaceDesc2TextMapHash")
    private String questReplaceDesc2TextMapHash;

    // 关联任务 ID
    @Field("questID1")
    private Integer questID1;
}
