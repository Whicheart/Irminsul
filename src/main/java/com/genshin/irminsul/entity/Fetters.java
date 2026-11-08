package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field("isHiden")
    private Boolean isHiden;

    @Field("voiceTitleTextMapHash")
    private String voiceTitleTextMapHash;

    @Field("voiceFileTextTextMapHash")
    private String voiceFileTextTextMapHash;

    @Field("voiceTitleLockedTextMapHash")
    private String voiceTitleLockedTextMapHash;

    @Field("tips")
    private List<String> tips;

    @Field("voiceFile")
    private String voiceFile;

    @Field("openConds")
    private List<OpenCond> openConds;

    @Field("hideCostumeList")
    private List<Integer> hideCostumeList;

    @Field("showCostumeList")
    private List<Integer> showCostumeList;

    @Data
    public static class OpenCond {

        @Field("condType")
        private String condType;

        @Field("paramList")
        private List<Integer> paramList;
    }
}
