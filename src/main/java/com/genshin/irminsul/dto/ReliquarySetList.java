package com.genshin.irminsul.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReliquarySetList {

    private List<RelicSetBrief> relicSets;

    @Data
    public static class RelicSetBrief {

        private Integer setId;

        // 筛选条件
        private String name;
        private Integer maxRankLevel;

        // 图像资源
        private String setIconName;
    }
}
