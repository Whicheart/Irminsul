package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster_title")
public class MonsterTitle {

    @Id
    private Integer id;

    @Field("isInverted")
    private Boolean isInverted;

    @Field("titleNameTextMapHash")
    private String titleNameTextMapHash;
}
