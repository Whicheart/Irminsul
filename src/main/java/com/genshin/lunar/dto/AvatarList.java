package com.genshin.lunar.dto;

import java.util.List;

import lombok.Data;

@Data
public class AvatarList {
    private List<AvatarBrief> avatars;

    @Data
    public static class AvatarBrief {
        private Integer id;
        private Integer sortId;
        private String name;
        private String qualityType;
        private String element;
        private String weaponType;
    }
}
