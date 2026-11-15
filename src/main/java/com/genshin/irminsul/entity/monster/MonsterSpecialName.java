package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster_special_name")
public class MonsterSpecialName {

    @Id
    private Integer id;

    @Field("specialNameLabID")
    private Integer specialNameLabID;

    @Field("specialNameTextMapHash")
    private String specialNameTextMapHash;

    @Field("isInRandomList")
    private Boolean isInRandomList;
}
