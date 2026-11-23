package com.genshin.irminsul.dto.reliquary;

import java.util.List;
import lombok.Data;

/** 各星级圣遗物的主副词条档位 */
@Data
public class ReliquaryPropRankDTO {

    // 按星级分组
    private List<RankLevel> rankLevels;

    // 单个星级
    @Data
    public static class RankLevel {

        // 星级
        private Integer rankLevel;

        // 主属性档位
        private List<MainProp> mainProps;
        // 副词条档位
        private List<AppendProp> appendProps;
    }

    // 主属性
    @Data
    public static class MainProp {

        // 属性类型
        private String propType;
        // 各档数值
        private List<Double> values;
    }

    // 副词条
    @Data
    public static class AppendProp {

        // 属性类型
        private String propType;
        // 各档数值
        private List<Double> values;
    }
}
