package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "weapon_level")
public class WeaponLevel {

    @Id
    private Integer level;

    @Field("requiredExps")
    private List<Integer> requiredExps;
}
