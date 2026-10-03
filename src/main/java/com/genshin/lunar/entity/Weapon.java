package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "weapon")
public class Weapon {
    @Id
    private Integer id;
}
