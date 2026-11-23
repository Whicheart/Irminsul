package com.genshin.irminsul.entity.avatar;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色风之翼配置 */
@Data
@Document(collection = "avatar_flycloak")
public class AvatarFlycloak {

    // 风之翼 ID
    @Id
    private Integer id;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 图标名
    @Field("icon")
    private String icon;

    // 是否隐藏
    @Field("hide")
    private Boolean hide;
}
