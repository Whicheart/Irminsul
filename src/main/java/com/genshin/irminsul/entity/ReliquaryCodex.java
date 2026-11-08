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

    @Field("suitId")
    private Integer suitId;

    @Field("sortOrder")
    private Integer sortOrder;

    @Field("level")
    private Integer level;

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
