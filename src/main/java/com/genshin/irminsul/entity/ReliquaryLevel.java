package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_level")
public class ReliquaryLevel {
    @Id
    private Integer id;

    // 各等级的经验与属性成长
    @Field("levels")
    private List<Level> levels;

    @Data
    public static class Level {
        @Field("level")
        private Integer level;

        @Field("exp")
        private Integer exp;

        @Field("addProps")
        private List<AddProp> addProps;
    }

    @Data
    public static class AddProp {
        @Field("propType")
        private String propType;

        @Field("value")
        private Double value;
    }
}
