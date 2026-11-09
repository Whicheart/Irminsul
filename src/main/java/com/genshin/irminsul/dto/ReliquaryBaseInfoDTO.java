package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class ReliquaryBaseInfoDTO {

    private Integer setId;

    // 基础信息
    private String name;
    private List<Integer> setAffixNum;
    private List<String> setAffixDesc;
    private Integer maxRankLevel;

    // 图像资源
    private String setIconName;

    // 套装内容
    private List<Contains> contains;

    @Data
    public static class Contains {

        private Integer id;

        // 基础信息
        private String name;
        private String desc;

        // 图像资源
        private String iconName;

        // 故事
        private List<String> story;
    }
}
