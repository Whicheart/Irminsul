package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物关系配置 */
@Data
@Document(collection = "monster_relationship")
public class MonsterRelationship {

    // 关系 ID
    @Id
    private Integer id;

    // 关系标签
    @Field("tagStr")
    private String tagStr;

    // 怪物稀有度
    @Field("monsterRarity")
    private String monsterRarity;

    // 名称
    @Field("name")
    private String name;
}
