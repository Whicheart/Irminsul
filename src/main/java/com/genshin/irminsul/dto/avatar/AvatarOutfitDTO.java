package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

/** 角色装扮数据 */
@Data
public class AvatarOutfitDTO {

    // 角色 ID
    private Integer id;

    // 装扮列表
    private List<Outfit> outfits;

    // 单个装扮
    @Data
    public static class Outfit {

        // 装扮 ID
        private Integer id;

        // 名称
        private String name;
        // 描述
        private String desc;
        // 品质
        private Integer quality;
        // 展示顺序
        private Integer indexID;
        // 是否默认装扮
        private Boolean isDefault;

        // 立绘名
        private String imageName;
        // 角色卡面名
        private String avatarCardName;
        // 装扮卡面名
        private String costumeCardName;
    }
}
