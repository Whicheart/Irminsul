package com.genshin.irminsul.entity.weapon;

import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 武器属性成长曲线，主键为等级 */
@Data
@Document(collection = "weapon_curve")
public class WeaponCurve {

    // 等级
    @Id
    private Integer level;

    // 各属性曲线系数
    @Field("curves")
    private Map<String, Double> curves;
}
