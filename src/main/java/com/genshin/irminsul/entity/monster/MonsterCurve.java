package com.genshin.irminsul.entity.monster;

import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster_curve")
public class MonsterCurve {

    @Id
    private Integer id;

    @Field("curves")
    private Map<String, Double> curves;
}
