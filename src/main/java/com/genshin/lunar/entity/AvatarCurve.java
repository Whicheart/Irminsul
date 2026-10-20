package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "avatar_curve")
public class AvatarCurve {
    @Id
    private Integer level;

    @Field("curves")
    private Map<String, Double> curves;
}
