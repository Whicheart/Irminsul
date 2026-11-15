package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "animal_codex")
public class AnimalCodex {

    @Id
    private Integer id;

    @Field("describeId")
    private Integer describeId;

    @Field("type")
    private String type;

    @Field("subType")
    private String subType;

    @Field("countType")
    private String countType;

    @Field("pushTipsCodexId")
    private Integer pushTipsCodexId;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("extraDescTextMapHash")
    private List<String> extraDescTextMapHash;

    @Field("modelPath")
    private String modelPath;

    @Field("sortOrder")
    private Integer sortOrder;

    @Field("isDisuse")
    private Boolean isDisuse;

    @Field("isSeenActive")
    private Boolean isSeenActive;

    @Field("showOnlyUnlocked")
    private Boolean showOnlyUnlocked;
}
