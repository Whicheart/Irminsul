package com.genshin.lunar.dto;

import lombok.Data;

import java.util.List;

@Data
public class AvatarStoryDTO {
    private Integer id;
    private List<Story> stories;

    @Data
    public static class Story {
        private String title;
        private String text;
        private List<String> tips;
    }
}
