package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

/** 角色列表响应 */
@Data
public class AvatarListResponse {

    // 简要角色列表
    private List<AvatarBrief> avatars;

    // 列表项
    @Data
    public static class AvatarBrief {

        // 角色 ID
        private Integer id;

        // 图鉴排序号
        private Integer sortId;

        // 名称
        private String name;
        // 品质
        private String qualityType;
        // 元素
        private String element;
        // 武器类型
        private String weaponType;

        // 抽卡图像名
        private String GachaIconName;
        // 头像图像名
        private String iconName;
    }
}
