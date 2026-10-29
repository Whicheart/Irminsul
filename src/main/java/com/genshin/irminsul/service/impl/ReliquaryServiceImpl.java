package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.ReliquaryList;
import com.genshin.irminsul.entity.EquipAffix;
import com.genshin.irminsul.entity.Reliquary;
import com.genshin.irminsul.entity.ReliquarySet;
import com.genshin.irminsul.entity.ReliquaryStory;
import com.genshin.irminsul.repository.EquipAffixRepository;
import com.genshin.irminsul.repository.ReliquaryRepository;
import com.genshin.irminsul.repository.ReliquarySetRepository;
import com.genshin.irminsul.repository.ReliquaryStoryRepository;
import com.genshin.irminsul.service.ReliquaryService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final ReliquaryStoryRepository reliquaryStoryRepository;
    private final EquipAffixRepository equipAffixRepository;
    private final TextMapChsUtil textMapChsUtil;

    // 15000 号套装缺少 equipAffixId 与文本，列表不展示
    private static final Integer EXCLUDED_SET_ID = 15000;

    @Override
    public List<ReliquaryList> getReliquaryList() {
        return reliquarySetRepository.findAll().stream()
                .filter(set -> !EXCLUDED_SET_ID.equals(set.getId()))
                .sorted(Comparator.comparing(
                                ReliquarySet::getMaxRankLevel, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(ReliquarySet::getId, Comparator.reverseOrder()))
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
        ReliquaryStory story = reliquaryStoryRepository.findById(set.getId()).orElse(null);
        List<List<String>> stories =
                story == null || story.getStories() == null ? new ArrayList<>() : story.getStories();
        List<Integer> containsList = set.getContainsList();
        for (Integer reliquaryId : containsList) {
            Reliquary reliquary = reliquaryMap.get(reliquaryId);
            ReliquaryBaseInfoDTO.Contains item = new ReliquaryBaseInfoDTO.Contains();
            item.setId(reliquaryId);
            item.setName(reliquary == null ? null : textMapChsUtil.getText(reliquary.getNameTextMapHash()));
            item.setDesc(reliquary == null ? null : textMapChsUtil.getText(reliquary.getDescTextMapHash()));
            item.setContainIconName("UI_RelicIcon_" + reliquaryId);
            int storyIndex = stories.size() == 1 ? 0 : getStoryIndex(reliquaryId);
            item.setStory(storyIndex < stories.size() ? stories.get(storyIndex) : new ArrayList<>());
            contains.add(item);
        }
        return contains;
    }

    // 部件 ID 从左往右第四位数字减一，即对应套装故事中的章节下标（如 47442 -> 第 3 号故事）
    private int getStoryIndex(Integer reliquaryId) {
        String id = String.valueOf(reliquaryId);
        if (id.length() < 4) {
            return 0;
        }
        return Math.max(Character.getNumericValue(id.charAt(3)) - 1, 0);
    }

    private EquipAffix getEquipAffix(ReliquarySet set) {
        if (set.getEquipAffixId() == null) {
            return null;
        }
        return equipAffixRepository.findById(set.getEquipAffixId()).orElse(null);
    }
}
