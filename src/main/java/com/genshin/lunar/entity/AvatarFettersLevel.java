package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_fetters_level")
public class AvatarFettersLevel {
    @Id
    private Integer level;

    @Field("needExp")
    private Integer needExp;
}
