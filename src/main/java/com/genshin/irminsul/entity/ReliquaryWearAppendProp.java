package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_wear_append_prop")
public class ReliquaryWearAppendProp {
    @Id
    private ObjectId id;

    // 可出现的副词条 ID 列表
    @Field("appendPropId")
    private List<Integer> appendPropId;
}
