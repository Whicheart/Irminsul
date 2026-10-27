package com.genshin.irminsul.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarVoiceDTO {
    private Integer id;
    private List<Daily> dailies;
    private List<Battle> battles;

    @Data
    public static class Daily {
        private String title;
        private String text;
        private List<String> tips;
    }

    @Data
    public static class Battle {
        private String title;
        private String text;
        private List<String> tips;
    }
}
