package com.genshin.irminsul.entity.monster;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物词缀配置 */
@Data
@Document(collection = "monster_affix")
public class MonsterAffix {

    // 词缀 ID
    @Id
    private Integer id;

    // 词缀名
    @Field("affix")
    private String affix;

    // 能力名列表
    @Field("abilityName")
    private List<String> abilityName;

    // 备注
    @Field("comment")
    private String comment;

    // 图标路径
    @Field("iconPath")
    private String iconPath;

    // 是否通用
    @Field("isCommon")
    private Boolean isCommon;

    // 是否合法
    @Field("isLegal")
    private String isLegal;
}
