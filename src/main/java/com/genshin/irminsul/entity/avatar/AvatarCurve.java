package com.genshin.irminsul.entity.avatar;

import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色属性成长曲线，主键为等级 */
@Data
@Document(collection = "avatar_curve")
public class AvatarCurve {

    // 等级
    @Id
    private Integer level;

    // 各属性曲线系数
    @Field("curves")
    private Map<String, Double> curves;
}
