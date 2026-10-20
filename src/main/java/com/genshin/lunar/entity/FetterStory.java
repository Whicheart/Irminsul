package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "fetter_story")
public class FetterStory {
    @Id
    private String id;

    @Field("avatarId")
    private Integer avatarId;
    @Field("fetterId")
    private Integer fetterId;

    @Field("storyTitleTextMapHash")
    private String storyTitleTextMapHash;
    @Field("storyContextTextMapHash")
    private String storyContextTextMapHash;
    @Field("storyTitle2TextMapHash")
    private String storyTitle2TextMapHash;
    @Field("storyContext2TextMapHash")
    private String storyContext2TextMapHash;

    @Field("tips")
    private List<String> tips;
}
