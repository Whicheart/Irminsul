package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarStoryDTO {

    private Integer id;

    private List<Story> stories;

    @Data
    public static class Story {

        // 解锁条件
        private List<String> tips;

        // 标题与内容
        private String title;
        private String text;
    }
}
