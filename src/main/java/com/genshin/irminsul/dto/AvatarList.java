package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarList {

    private List<AvatarBrief> avatars;

    @Data
    public static class AvatarBrief {

        private Integer id;

        // 排序
        private Integer sortId;

        // 筛选条件
        private String name;
        private String qualityType;
        private String element;
        private String weaponType;

        // 图像资源
        private String GachaIconName;
        private String iconName;
    }
}
