package com.genshin.irminsul.entity.reliquary;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 圣遗物故事配置 */
@Data
@Document(collection = "relic_story")
public class ReliquaryStory {

    // 故事 ID
    @Id
    private Integer id;

    // 按部件组织的故事段落
    @Field("stories")
    private List<List<String>> stories;
}
