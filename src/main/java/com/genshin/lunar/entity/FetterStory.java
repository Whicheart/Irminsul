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

    // 所属角色与故事编号
    @Field("avatarId")
    private Integer avatarId;
    @Field("fetterId")
    private Integer fetterId;

    // 标题与正文（含另一版）
    @Field("storyTitleTextMapHash")
    private String storyTitleTextMapHash;
    @Field("storyContextTextMapHash")
    private String storyContextTextMapHash;
    @Field("storyTitle2TextMapHash")
    private String storyTitle2TextMapHash;
    @Field("storyContext2TextMapHash")
    private String storyContext2TextMapHash;

    // 提示哈希
    @Field("tips")
    private List<String> tips;
}
