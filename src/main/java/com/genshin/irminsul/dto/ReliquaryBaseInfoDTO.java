package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class ReliquaryBaseInfoDTO {
    private Integer setId;
    private String name;
    private List<Integer> setAffixNum;
    private List<String> setAffixDesc;
    private String setIconName;
    private Integer maxRankLevel;
    private List<Contains> contains;

    @Data
    public static class Contains {
        private Integer id;
        private String name;
        private String desc;
        private String containIconName;
        private List<List<String>> stories;
    }
}
