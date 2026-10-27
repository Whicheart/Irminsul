package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "fetter_info")
public class FetterInfo {
    @Id
    private Integer id;

    // 好感条目 ID
    @Field("fetterId")
    private Integer fetterId;

    // 称号与简介的文本哈希
    @Field("avatarTitleTextMapHash")
    private String avatarTitleTextMapHash;

    @Field("avatarDetailTextMapHash")
    private String avatarDetailTextMapHash;

    // 权能、神之眼、所属、命之座的"前·后"两版文本
    @Field("avatarPowerBeforTextMapHash")
    private String avatarPowerBeforTextMapHash;

    @Field("avatarPowerAfterTextMapHash")
    private String avatarPowerAfterTextMapHash;

    @Field("avatarVisionBeforTextMapHash")
    private String avatarVisionBeforTextMapHash;

    @Field("avatarVisionAfterTextMapHash")
    private String avatarVisionAfterTextMapHash;

    @Field("avatarNativeTextMapHash")
    private String avatarNativeTextMapHash;

    @Field("avatarNative2TextMapHash")
    private String avatarNative2TextMapHash;

    @Field("avatarConstellationBeforTextMapHash")
    private String avatarConstellationBeforTextMapHash;

    @Field("avatarConstellationAfterTextMapHash")
    private String avatarConstellationAfterTextMapHash;

    // 生日（月、日）
    @Field("infoBirthMonth")
    private Integer infoBirthMonth;

    @Field("infoBirthDay")
    private Integer infoBirthDay;

    // 四语配音名
    @Field("cvChineseTextMapHash")
    private String cvChineseTextMapHash;

    @Field("cvEnglishTextMapHash")
    private String cvEnglishTextMapHash;

    @Field("cvJapaneseTextMapHash")
    private String cvJapaneseTextMapHash;

    @Field("cvKoreanTextMapHash")
    private String cvKoreanTextMapHash;
}
