package com.genshin.irminsul.dto.reliquary;

import java.util.List;
import lombok.Data;

/** 圣遗物套装列表响应 */
@Data
public class ReliquarySetListResponse {

    // 简要套装列表
    private List<RelicSetBrief> relicSets;

    // 列表项
    @Data
    public static class RelicSetBrief {

        // 套装 ID
        private Integer setId;

        // 名称
        private String name;
        // 最高星级
        private Integer maxRankLevel;

        // 套装图标名
        private String setIconName;
    }
}
