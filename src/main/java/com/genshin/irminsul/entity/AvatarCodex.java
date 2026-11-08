package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_codex")
public class AvatarCodex {

    @Id
    private Integer id;

    @Field("sortFactor")
    private Integer sortFactor;

    @Field("sortId")
    private Integer sortId;

    @Field("beginTime")
    private String beginTime;

    @Field("hideWhenDontHave")
    private Boolean hideWhenDontHave;
}
