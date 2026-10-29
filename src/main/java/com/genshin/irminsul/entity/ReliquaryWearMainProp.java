package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_wear_main_prop")
public class ReliquaryWearMainProp {
    @Id
    private ObjectId id;

    // 部位（EQUIP_SHOES 等）
    @Field("equipType")
    private String equipType;

    // 该部位可出现的主词条 ID
    @Field("mainPropId")
    private List<Integer> mainPropId;
}
