package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "avatar_talent")
public class AvatarTalent {

    @Id
    private Integer id;

    @Field("nameTextMapHash")
    private String nameTextMapHash;

    @Field("descTextMapHash")
    private String descTextMapHash;

    @Field("descExtraTextMapHash")
    private String descExtraTextMapHash;

    @Field("icon")
    private String icon;

    @Field("paramList")
    private List<Double> paramList;

    @Field("addProps")
    private List<AddProp> addProps;

    @Field("mainCostItemId")
    private Integer mainCostItemId;

    @Field("mainCostItemCount")
    private Integer mainCostItemCount;

    @Field("prevTalent")
    private Integer prevTalent;

    @Field("openConfig")
    private String openConfig;

    @Data
    public static class AddProp {

        @Field("propType")
        private String propType;

        @Field("value")
        private Double value;
    }
}
