package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_affix")
public class ReliquaryAffix {

    @Id
    private Integer id;

    @Field("depotId")
    private Integer depotId;

    @Field("groupId")
    private Integer groupId;

    @Field("propType")
    private String propType;

    @Field("propValue")
    private Double propValue;
}
