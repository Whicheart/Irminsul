package com.genshin.irminsul.dto.reliquary;

import java.util.List;
import lombok.Data;

/** 圣遗物套装详情 */
@Data
public class ReliquaryBaseInfoResponse {

    // 套装 ID
    private Integer setId;

    // 套装名称
    private String name;
    // 各档生效件数
    private List<Integer> setAffixNum;
    // 各档套装效果
    private List<String> setAffixDesc;
    // 最高星级
    private Integer maxRankLevel;

    // 套装图标名
    private String setIconName;

    // 部件列表
    private List<Contains> contains;

    // 套装部件
    @Data
    public static class Contains {

        // 部件 ID
        private Integer id;

        // 名称
        private String name;
        // 描述
        private String desc;

        // 图标名
        private String iconName;

        // 部件故事
        private List<String> story;
    }
}
