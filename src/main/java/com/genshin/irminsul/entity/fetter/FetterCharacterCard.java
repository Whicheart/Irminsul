package com.genshin.irminsul.entity.fetter;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色名片配置 */
@Data
@Document(collection = "fetter_character_card")
public class FetterCharacterCard {

    // 名片 ID
    @Id
    private String id;

    // 所属角色 ID
    @Field("avatarId")
    private Integer avatarId;

    // 解锁所需好感等级
    @Field("fetterLevel")
    private Integer fetterLevel;

    // 奖励 ID
    @Field("rewardId")
    private Integer rewardId;
}
