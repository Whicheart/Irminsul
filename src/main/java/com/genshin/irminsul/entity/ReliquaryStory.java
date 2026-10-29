package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_story")
public class ReliquaryStory {
    @Id
    private Integer id;

    @Field("stories")
    private List<List<String>> stories;
}
