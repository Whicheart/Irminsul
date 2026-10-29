package com.genshin.irminsul.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "reliquary_affix_normalized_param")
public class ReliquaryAffixNormalizedParam {
    @Id
    private Integer id;

    // 属性类型与归一化参数
    @Field("propType")
    private String propType;

    @Field("param")
    private Double param;

    /*
    // === 未知字段（混淆） ===
    @Field("NHBDFNHABCL")
    private Boolean NHBDFNHABCL;
     */
}
