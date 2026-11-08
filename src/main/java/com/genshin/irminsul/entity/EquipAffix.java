package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "equip_affix")
public class EquipAffix {

    @Id
    private Integer id;

    @Field("levels")
    private List<AffixLevel> levels;

    @Data
    public static class AffixLevel {
        @Field("level")
        private Integer level;

        @Field("affixId")
        private Integer affixId;

        @Field("nameTextMapHash")
        private String nameTextMapHash;

        @Field("descTextMapHash")
        private String descTextMapHash;

        @Field("openConfig")
        private String openConfig;

        @Field("paramList")
        private List<Double> paramList;

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
