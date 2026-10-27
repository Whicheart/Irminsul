package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_trace_effect")
public class AvatarTraceEffect {
    @Id
    private Integer id;

    // 名称、描述与对应物品 ID
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("itemId")
    private Integer itemId;
}
