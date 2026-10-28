package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "weapon_story")
public class WeaponStory {
    @Id
    private Integer id;

    // 每章故事由多行文本组成
    @Field("stories")
    private List<List<String>> stories;
}
