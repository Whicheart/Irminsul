package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "avatar")
public class Avatar {
    @Id
    private Integer id;
}
