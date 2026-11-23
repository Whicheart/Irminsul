package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.reliquary.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.reliquary.ReliquaryPropRankDTO;
import com.genshin.irminsul.dto.reliquary.ReliquarySetList;
import java.util.List;

/**
 * 圣遗物业务接口
 */
public interface ReliquaryService {

    /**
     * 查询圣遗物套装列表
     *
     * @param name  套装名称关键词，可选
     * @param ranks 星级筛选，可选
     * @return 套装简要列表
     */
    List<ReliquarySetList.RelicSetBrief> getReliquaryList(String name, List<Integer> ranks);

    /**
     * 查询圣遗物套装详情
     *
     * @param id 套装 ID
     * @return 套装详情；不存在时返回 null
     */
    ReliquaryBaseInfoDTO getReliquaryBaseInfo(Integer id);

    /**
     * 查询各星级圣遗物的主副词条档位
     *
     * @return 按星级分组的词条档位
     */
    ReliquaryPropRankDTO getReliquaryPropRank();
}
