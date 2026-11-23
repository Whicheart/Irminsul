package com.genshin.irminsul.entity.monster;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物称号配置 */
@Data
@Document(collection = "monster_title")
public class MonsterTitle {

    // 称号 ID
    @Id
    private Integer id;

    // 是否倒置
    @Field("isInverted")
    private Boolean isInverted;

    // 称号文本哈希
    @Field("titleNameTextMapHash")
    private String titleNameTextMapHash;
}
