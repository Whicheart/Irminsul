package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.ReliquaryList;
import com.genshin.irminsul.entity.EquipAffix;
import com.genshin.irminsul.entity.Reliquary;
import com.genshin.irminsul.entity.ReliquarySet;
import com.genshin.irminsul.repository.EquipAffixRepository;
import com.genshin.irminsul.repository.ReliquaryRepository;
import com.genshin.irminsul.repository.ReliquarySetRepository;
import com.genshin.irminsul.service.ReliquaryService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReliquaryServiceImpl implements ReliquaryService {
    private final ReliquarySetRepository reliquarySetRepository;
    private final ReliquaryRepository reliquaryRepository;
    private final EquipAffixRepository equipAffixRepository;
    private final TextMapChsUtil textMapChsUtil;

    @Override
    public List<ReliquaryList> getReliquaryList() {
        return reliquarySetRepository.findAll().stream()
                .map(this::toReliquaryList)
                .collect(Collectors.toList());
    }

    @Override
    public ReliquaryBaseInfoDTO getReliquaryBaseInfo(Integer id) {
        ReliquarySet set = reliquarySetRepository.findById(id).orElse(null);
        if (set == null) {
            return null;
        }
        ReliquaryBaseInfoDTO detail = new ReliquaryBaseInfoDTO();
        detail.setSetId(set.getId());
        detail.setName(getSetName(set));
        detail.setSetAffixNum(set.getSetNeedNum());
        detail.setSetAffixDesc(getSetAffixDesc(set));
        detail.setSetIconName(buildSetIconName(set));
        detail.setMaxRankLevel(set.getMaxRankLevel());
        detail.setContains(buildContains(set));
        return detail;
    }

    private ReliquaryList toReliquaryList(ReliquarySet set) {
        ReliquaryList brief = new ReliquaryList();
        brief.setSetId(set.getId());
        brief.setName(getSetName(set));
        brief.setSetIconName(buildSetIconName(set));
        brief.setMaxRankLevel(set.getMaxRankLevel());
        return brief;
    }

    // 套装名取 equip_affix 首级词条的名称
    private String getSetName(ReliquarySet set) {
        EquipAffix affix = getEquipAffix(set);
        if (affix == null || affix.getLevels() == null || affix.getLevels().isEmpty()) {
            return null;
        }
        return textMapChsUtil.getText(affix.getLevels().get(0).getNameTextMapHash());
    }

    // 套装描述取 equip_affix 各级词条描述，按精炼等级顺序返回
    private List<String> getSetAffixDesc(ReliquarySet set) {
        EquipAffix affix = getEquipAffix(set);
        if (affix == null || affix.getLevels() == null) {
            return new ArrayList<>();
        }
        return affix.getLevels().stream()
                .map(level -> textMapChsUtil.getText(level.getDescTextMapHash()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 套装图标名固定为 UI_RelicIcon_<套装首个圣遗物 ID>
    private String buildSetIconName(ReliquarySet set) {
        if (set.getContainsList() == null || set.getContainsList().isEmpty()) {
            return null;
        }
        return "UI_RelicIcon_" + set.getContainsList().get(0);
    }

    private List<ReliquaryBaseInfoDTO.Contains> buildContains(ReliquarySet set) {
        List<ReliquaryBaseInfoDTO.Contains> contains = new ArrayList<>();
        if (set.getContainsList() == null || set.getContainsList().isEmpty()) {
            return contains;
        }
        Map<Integer, Reliquary> reliquaryMap = reliquaryRepository.findAllById(set.getContainsList()).stream()
                .collect(Collectors.toMap(Reliquary::getId, reliquary -> reliquary));
        for (Integer reliquaryId : set.getContainsList()) {
            Reliquary reliquary = reliquaryMap.get(reliquaryId);
            ReliquaryBaseInfoDTO.Contains item = new ReliquaryBaseInfoDTO.Contains();
            item.setId(reliquaryId);
            item.setName(reliquary == null ? null : textMapChsUtil.getText(reliquary.getNameTextMapHash()));
            item.setDesc(reliquary == null ? null : textMapChsUtil.getText(reliquary.getDescTextMapHash()));
            item.setContainIconName("UI_RelicIcon_" + reliquaryId);
            item.setStories(new ArrayList<>());
            contains.add(item);
        }
        return contains;
    }

    private EquipAffix getEquipAffix(ReliquarySet set) {
        if (set.getEquipAffixId() == null) {
            return null;
        }
        return equipAffixRepository.findById(set.getEquipAffixId()).orElse(null);
    }
}
