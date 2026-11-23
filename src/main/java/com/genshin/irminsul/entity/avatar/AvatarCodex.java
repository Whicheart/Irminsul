package com.genshin.irminsul.entity.avatar;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色图鉴配置 */
@Data
@Document(collection = "avatar_codex")
public class AvatarCodex {

    // 图鉴 ID
    @Id
    private Integer id;

    // 排序因子
    @Field("sortFactor")
    private Integer sortFactor;

    // 排序号
    @Field("sortId")
    private Integer sortId;

    // 起始时间
    @Field("beginTime")
    private String beginTime;

    // 未拥有时隐藏
    @Field("hideWhenDontHave")
    private Boolean hideWhenDontHave;
}
