package com.genshin.irminsul.dto.monster;

import java.util.List;
import lombok.Data;

@Data
public class MonsterBaseInfoDTO {

    private Integer id;

    // 名字
    private String title;
    private String name;
    private List<String> specialName;
    private List<String> description;

    // 三维
    private List<Double> hp;
    private List<Double> attack;
    private List<Double> defense;

    // 抗性
    private Double fireSubHurt;
    private Double waterSubHurt;
    private Double iceSubHurt;
    private Double elecSubHurt;
    private Double windSubHurt;
    private Double rockSubHurt;
    private Double grassSubHurt;
    private Double physicalSubHurt;

    // 图像资源
    private String iconName;
}
