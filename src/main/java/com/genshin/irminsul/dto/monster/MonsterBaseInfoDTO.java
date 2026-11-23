package com.genshin.irminsul.dto.monster;

import java.util.List;
import lombok.Data;

/** 敌对生物基础信息 */
@Data
public class MonsterBaseInfoDTO {

    // 生物 ID
    private Integer id;

    // 称号
    private String title;
    // 名称
    private String name;
    // 特殊名列表
    private List<String> specialName;
    // 图鉴描述段落
    private List<String> description;

    // 逐级生命值
    private List<Double> hp;
    // 逐级攻击力
    private List<Double> attack;
    // 逐级防御力
    private List<Double> defense;

    // 火元素抗性
    private Double fireSubHurt;
    // 水元素抗性
    private Double waterSubHurt;
    // 冰元素抗性
    private Double iceSubHurt;
    // 雷元素抗性
    private Double elecSubHurt;
    // 风元素抗性
    private Double windSubHurt;
    // 岩元素抗性
    private Double rockSubHurt;
    // 草元素抗性
    private Double grassSubHurt;
    // 物理抗性
    private Double physicalSubHurt;

    // 图鉴头像名
    private String iconName;
}
