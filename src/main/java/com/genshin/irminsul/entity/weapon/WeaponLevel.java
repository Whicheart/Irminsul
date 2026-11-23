package com.genshin.irminsul.entity.weapon;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 武器等级经验配置 */
@Data
@Document(collection = "weapon_level")
public class WeaponLevel {

    // 等级
    @Id
    private Integer level;

    // 升级所需经验列表
    @Field("requiredExps")
    private List<Integer> requiredExps;
}
