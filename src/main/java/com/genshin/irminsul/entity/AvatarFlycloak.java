package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_flycloak")
public class AvatarFlycloak {

    @Id
    private Integer id;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("icon")
    private String icon;

    @Field("jsonName")
    private String jsonName;

    @Field("prefabPath")
    private String prefabPath;

    @Field("hide")
    private Boolean hide;
}
