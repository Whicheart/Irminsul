package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class ReliquaryPropRankDTO {
    private List<RankLevel> rankLevels;

    @Data
    public static class RankLevel {
        private Integer rankLevel;
        private List<MainProp> mainProps;
        private List<AppendProp> appendProps;
    }

    @Data
    public static class MainProp {
        private String propType;
        private List<Double> values;
    }

    @Data
    public static class AppendProp {
        private String propType;
        private List<Double> values;
    }
}
