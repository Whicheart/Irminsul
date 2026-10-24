package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "text_map_en_medium")
public class TextMapEnMedium {
    @Id
    private String id;

    @Field("text")
    private String text;
}
