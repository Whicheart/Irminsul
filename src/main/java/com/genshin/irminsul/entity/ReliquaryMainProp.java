package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_main_prop")
public class ReliquaryMainProp {
    @Id
    private Integer id;

    // 属性类型
    @Field("propType")
    private String propType;

    // 主词条仓库
    @Field("propDepotId")
    private Integer propDepotId;

    // 词条名称的文本键（Reliquary_Main_Affix_*）
    @Field("affixName")
    private String affixName;
}
