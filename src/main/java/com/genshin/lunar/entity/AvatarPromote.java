package com.genshin.lunar.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_promote")
public class AvatarPromote {
    @Id
    private Integer id;

    // 各突破等级
    @Field("levels")
    private List<PromoteLevel> levels;

    // 单个突破等级
    @Data
    public static class PromoteLevel {
        @Field("promoteLevel")
        private Integer promoteLevel;

        // 该阶突破后解锁的等级上限
        @Field("unlockMaxLevel")
        private Integer unlockMaxLevel;

        // 摩拉消耗、材料消耗与属性加成
        @Field("scoinCost")
        private Integer scoinCost;

        @Field("costItems")
        private List<CostItem> costItems;

        @Field("addProps")
        private List<AddProp> addProps;
    }

    // 突破消耗的材料
    @Data
    public static class CostItem {
        @Field("id")
        private Integer id;

        @Field("count")
        private Integer count;
    }

    // 突破带来的属性加成
    @Data
    public static class AddProp {
        @Field("propType")
        private String propType;

        @Field("value")
        private Double value;
    }
}
