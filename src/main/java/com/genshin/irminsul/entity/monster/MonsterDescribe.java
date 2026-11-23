package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物图鉴描述 */
@Data
@Document(collection = "monster_describe")
public class MonsterDescribe {

    // 描述 ID
    @Id
    private Integer id;

    // 图标名
    @Field("icon")
    private String icon;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 特殊名分组 ID
    @Field("specialNameLabID")
    private Integer specialNameLabID;

    // 称号 ID
    @Field("titleID")
    private Integer titleID;
}
