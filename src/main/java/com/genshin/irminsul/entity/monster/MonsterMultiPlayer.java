package com.genshin.irminsul.entity.monster;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 怪物联机属性配置 */
@Data
@Document(collection = "monster_multi_player")
public class MonsterMultiPlayer {

    // 怪物 ID
    @Id
    private Integer id;

    // 韧性数值
    @Field("EndureNum")
    private List<Integer> endureNum;

    // 属性倍率
    @Field("PropPer")
    private List<PropPer> propPer;

    // 元素护盾
    @Field("ElementShield")
    private List<Double> elementShield;

    // 属性倍率条目
    @Data
    public static class PropPer {

        // 属性类型
        @Field("PropType")
        private String propType;

        // 属性值
        @Field("PropValue")
        private List<Double> propValue;
    }
}
