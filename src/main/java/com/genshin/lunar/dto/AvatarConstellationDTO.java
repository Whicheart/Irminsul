package com.genshin.lunar.dto;

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
        private String constellationIconName;
        //        private List<Double> paramList;
        //        private List<AddProp> addProps;
    }

    //    @Data
    //    public static class AddProp {
    //        private String propType;
    //        private Double value;
    //    }
}
