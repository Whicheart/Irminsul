package com.genshin.irminsul.entity.fetter;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 角色资料配置 */
@Data
@Document(collection = "fetter_info")
public class FetterInfo {

    // 资料 ID
    @Id
    private Integer id;

    // 好感分组 ID
    @Field("fetterId")
    private Integer fetterId;

    // 关联类型，决定神之眼或神之心的图标
    @Field("avatarAssocType")
    private String avatarAssocType;

    // 称号文本哈希
    @Field("avatarTitleTextMapHash")
    private String avatarTitleTextMapHash;

    // 详情文本哈希
    @Field("avatarDetailTextMapHash")
    private String avatarDetailTextMapHash;

    // 神之眼（前）文本哈希
    @Field("avatarPowerBeforTextMapHash")
    private String avatarPowerBeforTextMapHash;

    // 神之眼（后）文本哈希
    @Field("avatarPowerAfterTextMapHash")
    private String avatarPowerAfterTextMapHash;

    // 元素类型（前）文本哈希
    @Field("avatarVisionBeforTextMapHash")
    private String avatarVisionBeforTextMapHash;

    // 元素类型（后）文本哈希
    @Field("avatarVisionAfterTextMapHash")
    private String avatarVisionAfterTextMapHash;

    // 所属地区文本哈希
    @Field("avatarNativeTextMapHash")
    private String avatarNativeTextMapHash;

    // 所属地区文本哈希（二）
    @Field("avatarNative2TextMapHash")
    private String avatarNative2TextMapHash;

    // 命座名（前）文本哈希
    @Field("avatarConstellationBeforTextMapHash")
    private String avatarConstellationBeforTextMapHash;

    // 命座名（后）文本哈希
    @Field("avatarConstellationAfterTextMapHash")
    private String avatarConstellationAfterTextMapHash;

    // 生日月份
    @Field("infoBirthMonth")
    private Integer infoBirthMonth;

    // 生日日期
    @Field("infoBirthDay")
    private Integer infoBirthDay;

    // 中配姓名文本哈希
    @Field("cvChineseTextMapHash")
    private String cvChineseTextMapHash;

    // 英配姓名文本哈希
    @Field("cvEnglishTextMapHash")
    private String cvEnglishTextMapHash;

    // 日配姓名文本哈希
    @Field("cvJapaneseTextMapHash")
    private String cvJapaneseTextMapHash;

    // 韩配姓名文本哈希
    @Field("cvKoreanTextMapHash")
    private String cvKoreanTextMapHash;

    // 解锁条件
    @Field("finishConds")
    private List<FinishCond> finishConds;

    // 解锁条件条目
    @Data
    public static class FinishCond {

        // 条件类型
        @Field("condType")
        private String condType;

        // 条件参数
        @Field("paramList")
        private List<Integer> paramList;
    }
}
