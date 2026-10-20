package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "avatar_promote")
public class AvatarPromote {
    @Id
    private Integer id; // 对应原 avatarPromoteId

    @Field("levels")
    private List<PromoteLevel> levels;

    @Data
    public static class PromoteLevel {
        @Field("promoteLevel")
        private Integer promoteLevel;

        @Field("scoinCost")
        private Integer scoinCost;
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
}
