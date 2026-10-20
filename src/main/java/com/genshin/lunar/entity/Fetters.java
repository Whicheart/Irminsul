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

    @Field("avatarId")
    private Integer avatarId;
    @Field("fetterId")
    private Integer fetterId;

    @Field("type")
    private Integer type;

    @Field("voiceTitleTextMapHash")
    private String voiceTitleTextMapHash;
    @Field("voiceFileTextTextMapHash")
    private String voiceFileTextTextMapHash;
    @Field("tips")
    private List<String> tips;
}
