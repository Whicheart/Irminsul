package com.genshin.irminsul.entity.textmap;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 中文文本映射（主表），主键为文本哈希 */
@Data
@Document(collection = "text_map_chs")
public class TextMapChs implements TextMapEntry {

    // 文本哈希
    @Id
    private String id;

    // 文本内容
    @Field("text")
    private String text;
}
