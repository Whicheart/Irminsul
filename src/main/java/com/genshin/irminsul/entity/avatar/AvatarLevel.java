package com.genshin.irminsul.entity.avatar;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色等级经验配置 */
@Data
@Document(collection = "avatar_level")
public class AvatarLevel {
    // 等级
    @Id
    private Integer level;

    // 升级所需经验
    @Field("exp")
    private Integer exp;
}
