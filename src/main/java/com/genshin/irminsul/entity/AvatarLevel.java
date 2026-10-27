package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_level")
public class AvatarLevel {
    @Id
    private Integer level;

    // 升级所需经验
    @Field("exp")
    private Integer exp;
}
