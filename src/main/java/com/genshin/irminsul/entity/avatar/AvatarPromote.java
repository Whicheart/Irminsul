package com.genshin.irminsul.entity.avatar;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色突破配置 */
@Data
@Document(collection = "avatar_promote")
public class AvatarPromote {

    // 突破配置 ID
    @Id
    private Integer id;

    // 各突破阶段
    @Field("levels")
    private List<PromoteLevel> levels;

    // 单个突破阶段
    @Data
    public static class PromoteLevel {
        // 突破等级
        @Field("promoteLevel")
        private Integer promoteLevel;

        // 解锁等级上限
        @Field("unlockMaxLevel")
        private Integer unlockMaxLevel;

        // 所需玩家等级
        @Field("requiredPlayerLevel")
        private Integer requiredPlayerLevel;

        // 摩拉消耗
        @Field("scoinCost")
        private Integer scoinCost;

        // 材料条目
        @Field("costItems")
        private List<CostItem> costItems;

        // 属性加成
        @Field("addProps")
        private List<AddProp> addProps;
    }

    // 材料条目
    @Data
    public static class CostItem {

        // 材料 ID
        @Field("id")
        private Integer id;

        // 数量
        @Field("count")
        private Integer count;
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
