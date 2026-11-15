package com.genshin.irminsul.dto.monster;

import java.util.List;
import lombok.Data;

@Data
public class MonsterList {

    private List<MonsterBrief> monsters;

    @Data
    public static class MonsterBrief {

        private Integer id;

        private String name;
        private String iconName;
    }
}
