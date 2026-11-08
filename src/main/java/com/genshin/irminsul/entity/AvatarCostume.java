package com.genshin.irminsul.entity;

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

    @Field("sideIconName")
    private String sideIconName;

    @Field("jsonName")
    private String jsonName;

    @Field("frontIconName")
    private String frontIconName;

    @Field("hide")
    private Boolean hide;

    @Field("isDefaultUnlock")
    private Boolean isDefaultUnlock;

    @Field("isDefault")
    private Boolean isDefault;

    @Field("domesticHideInArtPreview")
    private Boolean domesticHideInArtPreview;
}
