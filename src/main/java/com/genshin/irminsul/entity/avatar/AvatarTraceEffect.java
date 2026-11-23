package com.genshin.irminsul.entity.avatar;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色特效配置 */
@Data
@Document(collection = "avatar_trace_effect")
public class AvatarTraceEffect {
    // 特效 ID
    @Id
    private Integer id;

    // 名称文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;

    // 描述文本哈希
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 道具 ID
    @Field("itemId")
    private Integer itemId;
}
