package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "fetter_character_card")
public class FetterCharacterCard {
    @Id
    private String id;

    @Field("avatarId")
    private Integer avatarId;
    @Field("fetterLevel")
    private Integer fetterLevel;
    @Field("rewardId")
    private Integer rewardId;
}
