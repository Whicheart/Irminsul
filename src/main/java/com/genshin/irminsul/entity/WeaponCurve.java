package com.genshin.irminsul.entity;

import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "weapon_curve")
public class WeaponCurve {
    @Id
    private Integer level;

    // 曲线名 → 该等级的取值
    @Field("curves")
    private Map<String, Double> curves;
}
