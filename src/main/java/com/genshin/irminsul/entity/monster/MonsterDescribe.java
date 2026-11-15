package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster_describe")
public class MonsterDescribe {

    @Id
    private Integer id;

    @Field("icon")
    private String icon;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("specialNameLabID")
    private Integer specialNameLabID;

    @Field("titleID")
    private Integer titleID;
}
