package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

/** 角色命座数据 */
@Data
public class AvatarConstellationDTO {

    // 角色 ID
    private Integer id;

    // 命座列表
    private List<Constellation> constellations;

    // 单个命座
    @Data
    public static class Constellation {

        // 命座 ID
        private Integer id;
        // 名称
        private String name;
        // 描述
        private String desc;
        // 加强描述
        private String descExtra;
        // 图标名
        private String iconName;
    }
}
