package com.genshin.irminsul.entity.monster;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "monster_multi_player")
public class MonsterMultiPlayer {

    @Id
    private Integer id;

    @Field("EndureNum")
    private List<Integer> endureNum;

    @Field("PropPer")
    private List<PropPer> propPer;

    @Field("ElementShield")
    private List<Double> elementShield;

    @Data
    public static class PropPer {

        @Field("PropType")
        private String propType;

        @Field("PropValue")
        private List<Double> propValue;
    }
}
