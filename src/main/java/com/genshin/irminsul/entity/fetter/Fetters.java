package com.genshin.irminsul.entity.fetter;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色语音配置 */
@Data
@Document(collection = "fetters")
public class Fetters {

    // 语音 ID
    @Id
    private String id;

    // 所属角色 ID
    @Field("avatarId")
    private Integer avatarId;

    // 好感分组 ID
    @Field("fetterId")
    private Integer fetterId;

    // 语音类型：1 日常，2 战斗
    @Field("type")
    private Integer type;

    // 是否隐藏
    @Field("isHiden")
    private Boolean isHiden;

    // 标题文本哈希
    @Field("voiceTitleTextMapHash")
    private String voiceTitleTextMapHash;

    // 文案文本哈希
    @Field("voiceFileTextTextMapHash")
    private String voiceFileTextTextMapHash;

    // 未解锁标题文本哈希
    @Field("voiceTitleLockedTextMapHash")
    private String voiceTitleLockedTextMapHash;

    // 解锁提示
    @Field("tips")
    private List<String> tips;

    // 语音文件名
    @Field("voiceFile")
    private String voiceFile;

    // 开放条件
    @Field("openConds")
    private List<OpenCond> openConds;

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
