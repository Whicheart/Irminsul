package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.ReliquaryList;
import com.genshin.irminsul.dto.ReliquaryPropRankDTO;
import java.util.List;

public interface ReliquaryService {
    List<ReliquaryList> getReliquaryList();

    ReliquaryBaseInfoDTO getReliquaryBaseInfo(Integer id);

    ReliquaryPropRankDTO getReliquaryPropRank();
}
