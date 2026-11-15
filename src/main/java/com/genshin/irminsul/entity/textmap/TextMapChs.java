package com.genshin.irminsul.entity.textmap;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "text_map_chs")
public class TextMapChs implements TextMapEntry {

    @Id
    private String id;

    @Field("text")
    private String text;
}
