package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_set")
public class ReliquarySet {

    @Id
    private Integer id;

    @Field("setIcon")
    private String setIcon;

    @Field("textList")
    private List<String> textList;

    @Field("setNeedNum")
    private List<Integer> setNeedNum;

    @Field("equipAffixId")
    private Integer equipAffixId;

    @Field("containsList")
    private List<Integer> containsList;

    @Field("maxRankLevel")
    private Integer maxRankLevel;

    @Field("bagSortValue")
    private Integer bagSortValue;

    @Field("dungeonGroup")
    private List<Integer> dungeonGroup;

    @Field("disableFilter")
    private Integer disableFilter;
}
