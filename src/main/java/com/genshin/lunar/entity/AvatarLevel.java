package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_level")
public class AvatarLevel {
    @Id
    private Integer level;

    @Field("exp")
    private Integer needExp;
}
