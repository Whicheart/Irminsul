package com.genshin.irminsul.dto.monster;

import java.util.List;
import lombok.Data;

/** 敌对生物列表响应 */
@Data
public class MonsterList {

    // 简要生物列表
    private List<MonsterBrief> monsters;

    // 列表项
    @Data
    public static class MonsterBrief {

        // 生物 ID
        private Integer id;

        // 名称
        private String name;
        // 子类型
        private String type;
        // 头像名
        private String iconName;
    }
}
