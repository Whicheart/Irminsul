package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field("finishConds")
    private List<FinishCond> finishConds;

    @Field("openConds")
    private List<OpenCond> openConds;

    @Data
    public static class FinishCond {

        @Field("condType")
        private String condType;

        @Field("paramList")
        private List<Integer> paramList;
    }

    @Data
    public static class OpenCond {

        @Field("condType")
        private String condType;

        @Field("paramList")
        private List<Integer> paramList;
    }
}
