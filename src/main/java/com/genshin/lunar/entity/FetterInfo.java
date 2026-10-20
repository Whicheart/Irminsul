package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "fetter_info")
public class FetterInfo {
    @Id
    private Integer id;

    @Field("fetterId")
    private Integer fetterId;

    @Field("avatarTitleTextMapHash")
    private String avatarTitleTextMapHash;
    @Field("avatarDetailTextMapHash")
    private String avatarDetailTextMapHash;

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

    @Field("infoBirthMonth")
    private Integer infoBirthMonth;
    @Field("infoBirthDay")
    private Integer infoBirthDay;

    @Field("cvChineseTextMapHash")
    private String cvChineseTextMapHash;
    @Field("cvEnglishTextMapHash")
    private String cvEnglishTextMapHash;
    @Field("cvJapaneseTextMapHash")
    private String cvJapaneseTextMapHash;
    @Field("cvKoreanTextMapHash")
    private String cvKoreanTextMapHash;
}
