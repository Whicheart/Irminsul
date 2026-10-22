package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_costume")
public class AvatarCostume {
    @Id
    private Integer id;

    // 所属角色 ID（注意字段名是 characterId）
    @Field("characterId")
    private Integer characterId;

    // 名称与描述的文本哈希
    @Field("nameTextMapHash")
    private String nameTextMapHash;
    @Field("descTextMapHash")
    private String descTextMapHash;

    // 品质、获取道具与默认装扮标记
    @Field("quality")
    private Integer quality;
    @Field("itemId")
    private Integer itemId;
    @Field("indexID")
    private Integer indexID;

    // 立绘资源名
    @Field("sideIconName")
    private String sideIconName;
    @Field("frontIconName")
    private String frontIconName;

    /*
    // === 不知道是啥的混淆字段 ===
    @Field("BAMFPODFABJ")
    private String BAMFPODFABJ;

    @Field("ADLCLKDIJLK")
    private String ADLCLKDIJLK;

    @Field("KOKPFLNIEFM")
    private String KOKPFLNIEFM;

    @Field("PPHKABFHIJP")
    private String PPHKABFHIJP;

    @Field("HJKKNCODEBP")
    private Boolean HJKKNCODEBP;

    @Field("ONKNBEKJPEG")
    private Boolean ONKNBEKJPEG;

    @Field("FDBALHJDEDG")
    private Integer FDBALHJDEDG;

    // === 不知道会不会用到的明文字段 ===
    @Field("hide")
    private Boolean hide;
     */
}
