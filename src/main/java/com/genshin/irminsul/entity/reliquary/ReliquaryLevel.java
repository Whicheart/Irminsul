package com.genshin.irminsul.entity.reliquary;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 圣遗物等级配置，主键为星级 */
@Data
@Document(collection = "reliquary_level")
public class ReliquaryLevel {

    // 星级
    @Id
    private Integer id;

    // 各等级配置
    @Field("levels")
    private List<Level> levels;

    // 单级配置
    @Data
    public static class Level {

        // 等级
        @Field("level")
        private Integer level;

        // 升级所需经验
        @Field("exp")
        private Integer exp;

        // 属性加成
        @Field("addProps")
        private List<AddProp> addProps;
    }

    // 属性加成条目
    @Data
    public static class AddProp {

        // 属性类型
        @Field("propType")
        private String propType;

        // 数值
        @Field("value")
        private Double value;
    }
}
