package com.genshin.irminsul.entity.weapon;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 武器图鉴配置 */
@Data
@Document(collection = "weapon_codex")
public class WeaponCodex {

    // 图鉴 ID
    @Id
    private Integer id;

    // 武器 ID
    @Field("weaponId")
    private Integer weaponId;

    // 排序号
    @Field("sortOrder")
    private Integer sortOrder;
}
