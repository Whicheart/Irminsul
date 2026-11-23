package com.genshin.irminsul.entity.textmap;

/** TextMap 条目通用访问接口，便于批量查询时统一处理 */
public interface TextMapEntry {

    /** 文本 ID */
    String getId();

    /** 中文文本 */
    String getText();
}
