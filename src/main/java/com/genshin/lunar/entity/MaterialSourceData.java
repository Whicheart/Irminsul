package com.genshin.lunar.entity;

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

    // 获取途径描述与来源标签
    @Field("textList")
    private List<String> textList;

    @Field("sourceList")
    private List<String> sourceList;
}
