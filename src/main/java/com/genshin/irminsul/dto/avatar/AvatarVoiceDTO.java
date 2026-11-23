package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

/** 角色语音 */
@Data
public class AvatarVoiceDTO {

    // 角色 ID
    private Integer id;

    // 日常语音
    private List<Daily> dailies;
    // 战斗语音
    private List<Battle> battles;

    // 日常语音条目
    @Data
    public static class Daily {

        // 解锁条件
        private List<String> tips;

        // 标题
        private String title;
        // 正文
        private String text;
    }

    // 战斗语音条目
    @Data
    public static class Battle {

        // 解锁条件
        private List<String> tips;

        // 标题
        private String title;
        // 正文
        private String text;
    }
}
