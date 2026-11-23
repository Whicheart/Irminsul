package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

/** 角色资料故事 */
@Data
public class AvatarStoryDTO {

    // 角色 ID
    private Integer id;

    // 故事列表
    private List<Story> stories;

    // 单条故事
    @Data
    public static class Story {

        // 解锁条件
        private List<String> tips;

        // 标题
        private String title;
        // 正文
        private String text;
    }
}
