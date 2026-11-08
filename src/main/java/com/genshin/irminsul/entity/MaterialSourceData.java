package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "material_source_data")
public class MaterialSourceData {

    @Id
    private Integer id;

    @Field("textList")
    private List<String> textList;

    @Field("sourceList")
    private List<String> sourceList;

    @Field("jumpDescs")
    private List<String> jumpDescs;

    @Field("jumpTargets")
    private List<String> jumpTargets;

    @Field("dungeonList")
    private List<Integer> dungeonList;

    @Field("dungeonGroup")
    private List<Integer> dungeonGroup;
}
