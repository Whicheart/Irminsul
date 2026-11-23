package com.genshin.irminsul.entity.monster;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster_affix")
public class MonsterAffix {

    @Id
    private Integer id;

    @Field("affix")
    private String affix;

    @Field("abilityName")
    private List<String> abilityName;

    @Field("comment")
    private String comment;

    @Field("iconPath")
    private String iconPath;

    @Field("isCommon")
    private Boolean isCommon;

    @Field("isLegal")
    private String isLegal;
}
