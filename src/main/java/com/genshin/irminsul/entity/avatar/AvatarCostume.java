package com.genshin.irminsul.entity.avatar;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色装扮配置 */
@Data
@Document(collection = "avatar_costume")
public class AvatarCostume {

    // 装扮 ID
    @Id
    private Integer id;

    // 所属角色 ID
    @Field("characterId")
    private Integer characterId;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 品质
    @Field("quality")
    private Integer quality;

    // 道具 ID
    @Field("itemId")
    private Integer itemId;

    // 展示顺序
    @Field("indexID")
    private Integer indexID;

    // 侧边图标名
    @Field("sideIconName")
    private String sideIconName;

    // 正面图标名
    @Field("frontIconName")
    private String frontIconName;

    // 是否隐藏
    @Field("hide")
    private Boolean hide;

    // 是否默认解锁
    @Field("isDefaultUnlock")
    private Boolean isDefaultUnlock;

    // 是否默认装扮
    @Field("isDefault")
    private Boolean isDefault;
}
