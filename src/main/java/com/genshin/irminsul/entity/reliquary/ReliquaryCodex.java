package com.genshin.irminsul.entity.reliquary;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 圣遗物图鉴配置 */
@Data
@Document(collection = "reliquary_codex")
public class ReliquaryCodex {

    // 图鉴 ID
    @Id
    private Integer id;

    // 套装 ID
    @Field("suitId")
    private Integer suitId;

    // 排序号
    @Field("sortOrder")
    private Integer sortOrder;

    // 星级
    @Field("level")
    private Integer level;

    // 生之花部件 ID
    @Field("flowerId")
    private Integer flowerId;

    // 空之杯部件 ID
    @Field("cupId")
    private Integer cupId;

    // 时之沙部件 ID
    @Field("sandId")
    private Integer sandId;

    // 理之冠部件 ID
    @Field("capId")
    private Integer capId;

    // 死之羽部件 ID
    @Field("leatherId")
    private Integer leatherId;
}
