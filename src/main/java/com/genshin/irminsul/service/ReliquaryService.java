package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.reliquary.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.reliquary.ReliquaryPropRankDTO;
import com.genshin.irminsul.dto.reliquary.ReliquarySetList;
import java.util.List;

public interface ReliquaryService {

    List<ReliquarySetList.RelicSetBrief> getReliquaryList(String name, List<Integer> ranks);

    ReliquaryBaseInfoDTO getReliquaryBaseInfo(Integer id);

    ReliquaryPropRankDTO getReliquaryPropRank();
}
