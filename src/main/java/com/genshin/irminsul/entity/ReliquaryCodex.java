package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_codex")
public class ReliquaryCodex {
    @Id
    private Integer id;

    // 套装与图鉴排序
    @Field("suitId")
    private Integer suitId;

    @Field("sortOrder")
    private Integer sortOrder;

    @Field("level")
    private Integer level;

    // 五个部位对应的圣遗物 ID
    @Field("flowerId")
    private Integer flowerId;

    @Field("cupId")
    private Integer cupId;

    @Field("sandId")
    private Integer sandId;

    @Field("capId")
    private Integer capId;

    @Field("leatherId")
    private Integer leatherId;
}
