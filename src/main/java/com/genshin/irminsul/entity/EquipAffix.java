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

    // 各精炼等级的词条
    @Field("levels")
    private List<AffixLevel> levels;

    @Data
    public static class AffixLevel {
        @Field("level")
        private Integer level;

        // 该等级对应的词条 ID
        @Field("affixId")
        private Integer affixId;

        // 名称与描述的文本哈希
        @Field("nameTextMapHash")
        private String nameTextMapHash;

        @Field("descTextMapHash")
        private String descTextMapHash;

        // 词条生效逻辑的配置名
        @Field("openConfig")
        private String openConfig;

        // 描述文案里引用的参数
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
