package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarVoiceDTO {

    private Integer id;

    // 日常/战斗
    private List<Daily> dailies;
    private List<Battle> battles;

    @Data
    public static class Daily {

        // 解锁条件
        private List<String> tips;

        // 标题与内容
        private String title;
        private String text;
    }

    @Data
    public static class Battle {

        // 解锁条件
        private List<String> tips;

        // 标题与内容
        private String title;
        private String text;
    }
}
