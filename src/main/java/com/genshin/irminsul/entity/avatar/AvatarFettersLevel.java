package com.genshin.irminsul.entity.avatar;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色好感等级配置 */
@Data
@Document(collection = "avatar_fetters_level")
public class AvatarFettersLevel {

    // 好感等级
    @Id
    private Integer level;

    // 升级所需好感经验
    @Field("needExp")
    private Integer needExp;
}
