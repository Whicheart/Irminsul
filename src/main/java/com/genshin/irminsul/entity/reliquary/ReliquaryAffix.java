package com.genshin.irminsul.entity.reliquary;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 圣遗物词条配置 */
@Data
@Document(collection = "reliquary_affix")
public class ReliquaryAffix {

    // 词条 ID
    @Id
    private Integer id;

    // 词条库 ID，首位数字表示星级
    @Field("depotId")
    private Integer depotId;

    // 分组 ID
    @Field("groupId")
    private Integer groupId;

    // 属性类型
    @Field("propType")
    private String propType;

    // 数值
    @Field("propValue")
    private Double propValue;
}
