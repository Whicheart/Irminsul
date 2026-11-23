package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物特殊名配置 */
@Data
@Document(collection = "monster_special_name")
public class MonsterSpecialName {

    // 特殊名 ID
    @Id
    private Integer id;

    // 特殊名分组 ID
    @Field("specialNameLabID")
    private Integer specialNameLabID;

    // 特殊名文本哈希
    @Field("specialNameTextMapHash")
    private String specialNameTextMapHash;
}
