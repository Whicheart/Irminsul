package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class WeaponList {
    private List<WeaponBrief> weapons;

    @Data
    public static class WeaponBrief {
        private Integer id;
        private Integer sortId;
        private String GachaIconName;
        private String name;
        private Integer rank;
        private String weaponType;
    }
}
