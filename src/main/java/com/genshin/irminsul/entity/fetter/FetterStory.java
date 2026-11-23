package com.genshin.irminsul.entity.fetter;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色故事配置 */
@Data
@Document(collection = "fetter_story")
public class FetterStory {

    // 故事 ID
    @Id
    private String id;

    // 所属角色 ID
    @Field("avatarId")
    private Integer avatarId;

    // 好感分组 ID
    @Field("fetterId")
    private Integer fetterId;

    // 标题文本哈希
    @Field("storyTitleTextMapHash")
    private String storyTitleTextMapHash;

    // 正文文本哈希
    @Field("storyContextTextMapHash")
    private String storyContextTextMapHash;

    // 第二标题文本哈希
    @Field("storyTitle2TextMapHash")
    private String storyTitle2TextMapHash;

    // 第二正文文本哈希
    @Field("storyContext2TextMapHash")
    private String storyContext2TextMapHash;

    // 解锁提示
    @Field("tips")
    private List<String> tips;

    // 完成条件
    @Field("finishConds")
    private List<FinishCond> finishConds;

    // 开放条件
    @Field("openConds")
    private List<OpenCond> openConds;

    // 完成条件条目
    @Data
    public static class FinishCond {

        // 条件类型
        @Field("condType")
        private String condType;

        // 条件参数
        @Field("paramList")
        private List<Integer> paramList;
    }

    // 开放条件条目
    @Data
    public static class OpenCond {

        // 条件类型
        @Field("condType")
        private String condType;

        // 条件参数
        @Field("paramList")
        private List<Integer> paramList;
    }
}
