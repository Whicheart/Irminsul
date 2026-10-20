package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_costume")
public class AvatarCostume {
    @Id
    private Integer id;

    @Field("characterId")
    private Integer characterId;

    @Field("nameTextMapHash")
    private String nameTextMapHash;
    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("quality")
    private Integer quality;
    @Field("itemId")
    private Integer itemId;
    @Field("indexID")
    private Integer indexID;
    @Field("isDefault")
    private Boolean isDefault;

    @Field("sideIconName")
    private String sideIconName;
    @Field("frontIconName")
    private String frontIconName;

    /*
    // === 不知道是啥的混淆字段 ===
    @Field("BAMFPODFABJ")
    private String BAMFPODFABJ;

    @Field("ADLCLKDIJLK")
    private String ADLCLKDIJLK;

    @Field("KOKPFLNIEFM")
    private String KOKPFLNIEFM;

    @Field("PPHKABFHIJP")
    private String PPHKABFHIJP;

    @Field("HJKKNCODEBP")
    private Boolean HJKKNCODEBP;

    @Field("ONKNBEKJPEG")
    private Boolean ONKNBEKJPEG;

    @Field("FDBALHJDEDG")
    private Integer FDBALHJDEDG;

    // === 不知道会不会用到的明文字段 ===
    @Field("hide")
    private Boolean hide;
     */
}
