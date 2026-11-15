package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

@Data
public class AvatarConstellationDTO {

    private Integer id;

    private List<Constellation> constellations;

    @Data
    public static class Constellation {

        private Integer id;
        private String name;
        private String desc;
        private String descExtra;
        private String iconName;
    }
}
