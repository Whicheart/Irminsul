package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_fetters_level")
public class AvatarFettersLevel {
    @Id
    private Integer level;

    // 升至下一级所需经验
    @Field("needExp")
    private Integer needExp;
}
