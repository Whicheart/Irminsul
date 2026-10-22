package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "text_map_chs_medium")
public class TextMapChsMedium {
    @Id
    private String id;

    // 哈希对应的中文文本
    @Field("text")
    private String text;
}
