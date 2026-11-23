package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 敌对生物图鉴条目 */
@Data
@Document(collection = "animal_codex")
public class AnimalCodex {

    // 图鉴 ID
    @Id
    private Integer id;

    // 描述表 ID，指向 monster_describe
    @Field("describeId")
    private Integer describeId;

    // 图鉴类型
    @Field("type")
    private String type;

    // 子类型
    @Field("subType")
    private String subType;

    // 计数类型
    @Field("countType")
    private String countType;

    // 推送提示 ID
    @Field("pushTipsCodexId")
    private Integer pushTipsCodexId;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 额外描述文本哈希列表
    @Field("extraDescTextMapHash")
    private List<String> extraDescTextMapHash;

    // 排序号
    @Field("sortOrder")
    private Integer sortOrder;
}
