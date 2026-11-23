package com.genshin.irminsul.entity.monster;

import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物属性成长曲线 */
@Data
@Document(collection = "monster_curve")
public class MonsterCurve {

    // 曲线 ID
    @Id
    private Integer id;

    // 各属性曲线系数
    @Field("curves")
    private Map<String, Double> curves;
}
