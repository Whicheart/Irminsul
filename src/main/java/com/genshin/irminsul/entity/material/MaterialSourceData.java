package com.genshin.irminsul.entity.material;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** 材料来源配置 */
@Data
@Document(collection = "material_source_data")
public class MaterialSourceData {

    // 材料 ID
    @Id
    private Integer id;

    // 来源文本
    @Field("textList")
    private List<String> textList;

    // 来源列表
    @Field("sourceList")
    private List<String> sourceList;

    // 跳转描述
    @Field("jumpDescs")
    private List<String> jumpDescs;

    // 跳转目标
    @Field("jumpTargets")
    private List<String> jumpTargets;

    // 关联副本 ID
    @Field("dungeonList")
    private List<Integer> dungeonList;

    // 副本分组 ID
    @Field("dungeonGroup")
    private List<Integer> dungeonGroup;
}
