package com.genshin.lunar.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarOutfitDTO {
    private Integer id;
    private List<Outfit> outfits;

    @Data
    public static class Outfit {
        private Integer id;
        private String name;
        private String desc;
        private Integer quality;
        private Integer indexID;
    }
}
