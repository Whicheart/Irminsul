package com.genshin.irminsul.entity.reliquary;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 圣遗物套装配置 */
@Data
@Document(collection = "reliquary_set")
public class ReliquarySet {

    // 套装 ID
    @Id
    private Integer id;

    // 套装图标名
    @Field("setIcon")
    private String setIcon;

    // 套装效果文本
    @Field("textList")
    private List<String> textList;

    // 各档生效件数
    @Field("setNeedNum")
    private List<Integer> setNeedNum;

    // 套装效果 ID
    @Field("equipAffixId")
    private Integer equipAffixId;

    // 部件 ID 列表
    @Field("containsList")
    private List<Integer> containsList;

    // 最高星级
    @Field("maxRankLevel")
    private Integer maxRankLevel;

    // 背包排序值
    @Field("bagSortValue")
    private Integer bagSortValue;

    // 副本分组 ID
    @Field("dungeonGroup")
    private List<Integer> dungeonGroup;
}
