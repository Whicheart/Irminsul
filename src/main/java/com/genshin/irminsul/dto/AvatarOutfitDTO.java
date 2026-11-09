package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarOutfitDTO {

    private Integer id;

    private List<Outfit> outfits;

    @Data
    public static class Outfit {

        private Integer id;

        // 基础信息
        private String name;
        private String desc;
        private Integer quality;
        private Integer indexID;
        private Boolean isDefault;

        // 图像资源
        private String imageName;
        private String avatarCardName;
        private String costumeCardName;
    }
}
