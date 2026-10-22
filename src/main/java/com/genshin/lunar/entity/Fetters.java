package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "fetters")
public class Fetters {
    @Id
    private String id;

    // 所属角色与语音编号
    @Field("avatarId")
    private Integer avatarId;
    @Field("fetterId")
    private Integer fetterId;

    // 1=日常语音，2=战斗语音
    @Field("type")
    private Integer type;

    // 标题、文本与提示哈希
    @Field("voiceTitleTextMapHash")
    private String voiceTitleTextMapHash;
    @Field("voiceFileTextTextMapHash")
    private String voiceFileTextTextMapHash;
    @Field("tips")
    private List<String> tips;
}
