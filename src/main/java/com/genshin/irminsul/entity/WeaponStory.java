package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "weapon_story")
public class WeaponStory {
    private Integer id;
    private List<Story> stories;

    @Data
    public static class Story {
        private List<String> story;
    }
}
