package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster_relationship")
public class MonsterRelationship {

    @Id
    private Integer id;

    @Field("tagStr")
    private String tagStr;

    @Field("monsterRarity")
    private String monsterRarity;

    @Field("name")
    private String name;
}
