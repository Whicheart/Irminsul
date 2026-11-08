package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "material_codex")
public class MaterialCodex {

    @Id
    private Integer id;

    @Field("materialId")
    private Integer materialId;

    @Field("type")
    private String type;

    @Field("sortOrder")
    private Integer sortOrder;

    @Field("icon")
    private String icon;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("questReplaceName1TextMapHash")
    private String questReplaceName1TextMapHash;

    @Field("questReplaceDesc1TextMapHash")
    private String questReplaceDesc1TextMapHash;

    @Field("questReplaceName2TextMapHash")
    private String questReplaceName2TextMapHash;

    @Field("questReplaceDesc2TextMapHash")
    private String questReplaceDesc2TextMapHash;

    @Field("questID1")
    private Integer questID1;

    @Field("isDisuse")
    private Boolean isDisuse;

    @Field("showOnlyUnlocked")
    private Boolean showOnlyUnlocked;
}
