package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "weapon_codex")
public class WeaponCodex {
    @Id
    private Integer id;

    @Field("weaponId")
    private Integer weaponId;

    @Field("sortOrder")
    private Integer sortOrder;
}
