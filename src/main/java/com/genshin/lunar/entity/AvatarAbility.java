package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_ability")
public class AvatarAbility {
    @Id
    private String id;

    @Field("character_key")
    private String characterKey;
    @Field("ability_name")
    private String abilityName;
    @Field("data")
    private AbilityData data;

    @Data
    public static class AbilityData {
        @Field("Default")
        private AbilityDataDefault dataDefault;
    }

    @Data
    public static class AbilityDataDefault {

    }
}
