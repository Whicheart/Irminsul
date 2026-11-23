package com.genshin.irminsul.entity.textmap;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 中文文本映射（中表），查询优先级高于 text_map_chs */
@Data
@Document(collection = "text_map_chs_medium")
public class TextMapChsMedium implements TextMapEntry {

    // 文本哈希
    @Id
    private String id;

    // 文本内容
    @Field("text")
    private String text;
}
