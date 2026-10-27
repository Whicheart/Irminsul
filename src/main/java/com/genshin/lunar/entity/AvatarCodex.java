package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_codex")
public class AvatarCodex {
    @Id
    private Integer id;

    // 排序权重
    @Field("sortFactor")
    private Integer sortFactor;

    @Field("sortId")
    private Integer sortId;

    // 上线时间（字符串，非日期类型）
    @Field("beginTime")
    private String beginTime;

    /*
    // === 未知布尔字段 ===
    @Field("OOBINIPDJEF")
    private Boolean OOBINIPDJEF;
     */
}
