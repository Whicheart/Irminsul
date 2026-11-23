package com.genshin.irminsul.entity.weapon;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 武器故事配置 */
@Data
@Document(collection = "weapon_story")
public class WeaponStory {

    // 故事 ID
    @Id
    private Integer id;

    // 各突破阶段故事
    @Field("stories")
    private List<List<String>> stories;
}
