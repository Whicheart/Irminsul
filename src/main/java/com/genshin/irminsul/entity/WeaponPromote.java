package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "weapon_promote")
public class WeaponPromote {
    @Id
    private Integer id;

    // 各突破等级
    @Field("levels")
    private List<PromoteLevel> levels;

    @Data
    public static class PromoteLevel {
        @Field("promoteLevel")
        private Integer promoteLevel;

        @Field("unlockMaxLevel")
        private Integer unlockMaxLevel;

        @Field("coinCost")
        private Integer coinCost;

        @Field("costItems")
        private List<CostItem> costItems;

        @Field("addProps")
        private List<AddProp> addProps;
    }

    @Data
    public static class CostItem {
        @Field("id")
        private Integer id;

        @Field("count")
        private Integer count;
    }

    @Data
    public static class AddProp {
        @Field("propType")
        private String propType;

        @Field("value")
        private Double value;
    }

    /*
    // === 应该用不到 ===
    @Field("requiredPlayerLevel")
    private Integer requiredPlayerLevel;
     */
}
