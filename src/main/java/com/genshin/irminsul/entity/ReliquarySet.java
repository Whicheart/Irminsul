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

    // 套装名与两件套/四件套描述的文本哈希
    @Field("setIcon")
    private String setIcon;

    // 生效件数（通常 2、4）与对应特效 ID
    @Field("setNeedNum")
    private List<Integer> setNeedNum;

    @Field("equipAffixId")
    private Integer equipAffixId;

    // 套装包含的圣遗物 ID
    @Field("containsList")
    private List<Integer> containsList;

    // 排序与筛选
    @Field("bagSortValue")
    private Integer bagSortValue;

    // 秘境/掉落组
    @Field("dungeonGroup")
    private List<Integer> dungeonGroup;

    @Field("maxRankLevel")
    private Integer maxRankLevel;

    /*
    // === 无解哈希 ===
    @Field("textList")
    private List<String> textList;
    @Field("disableFilter")
    private Integer disableFilter;
     */
}
