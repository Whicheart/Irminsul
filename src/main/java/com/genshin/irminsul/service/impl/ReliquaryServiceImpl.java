package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.ReliquaryPropRankDTO;
import com.genshin.irminsul.dto.ReliquarySetList;
import com.genshin.irminsul.entity.*;
import com.genshin.irminsul.repository.*;
import com.genshin.irminsul.service.ReliquaryService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReliquaryServiceImpl implements ReliquaryService {
    // 15000 号套装缺少 equipAffixId 与文本，15004/15012 无可用故事
    private static final Set<Integer> EXCLUDED_SET_IDS = Set.of(15000, 15004, 15012);

    // 火抗未实装
    private static final String FIRE_SUB_HURT = "FIGHT_PROP_FIRE_SUB_HURT";

    private final ReliquarySetRepository reliquarySetRepository;
    private final ReliquaryRepository reliquaryRepository;
    private final ReliquaryStoryRepository reliquaryStoryRepository;
    private final EquipAffixRepository equipAffixRepository;
    private final ReliquaryLevelRepository reliquaryLevelRepository;
    private final ReliquaryAffixRepository reliquaryAffixRepository;
    private final TextMapChsUtil textMapChsUtil;

    @Override
    public List<ReliquarySetList.RelicSetBrief> getReliquaryList() {
        List<ReliquarySet> sets = reliquarySetRepository.findAll().stream()
                .filter(set -> set.getId() != null && !EXCLUDED_SET_IDS.contains(set.getId()))
                .sorted(Comparator.comparing(
                                ReliquarySet::getMaxRankLevel, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(ReliquarySet::getId, Comparator.reverseOrder()))
                .toList();

        List<Integer> affixIds = sets.stream()
                .map(ReliquarySet::getEquipAffixId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        Map<Integer, EquipAffix> affixById = equipAffixRepository.findAllById(affixIds).stream()
                .collect(Collectors.toMap(EquipAffix::getId, affix -> affix, (existing, replacement) -> existing));

        Set<String> hashes = new LinkedHashSet<>();
        for (EquipAffix affix : affixById.values()) {
            EquipAffix.AffixLevel first = firstAffixLevel(affix);
            if (first != null) {
                hashes.add(first.getNameTextMapHash());
            }
        }
        Map<String, String> texts = textMapChsUtil.getTexts(hashes);

        List<ReliquarySetList.RelicSetBrief> result = new ArrayList<>();
        for (ReliquarySet set : sets) {
            ReliquarySetList.RelicSetBrief brief = new ReliquarySetList.RelicSetBrief();
            brief.setSetId(set.getId());
            EquipAffix affix = set.getEquipAffixId() == null ? null : affixById.get(set.getEquipAffixId());
            EquipAffix.AffixLevel first = affix == null ? null : firstAffixLevel(affix);
            brief.setName(first == null ? null : texts.get(first.getNameTextMapHash()));
            brief.setMaxRankLevel(set.getMaxRankLevel());
            brief.setSetIconName(set.getSetIcon());
            result.add(brief);
        }
        return result;
    }

    @Override
    public ReliquaryBaseInfoDTO getReliquaryBaseInfo(Integer id) {
        ReliquarySet set = reliquarySetRepository.findById(id).orElse(null);
        if (set == null) {
            return null;
        }

        EquipAffix affix = set.getEquipAffixId() == null
                ? null
                : equipAffixRepository.findById(set.getEquipAffixId()).orElse(null);
        List<EquipAffix.AffixLevel> affixLevels = sortedAffixLevels(affix);

        List<Reliquary> reliquaries = set.getContainsList() == null
                ? List.of()
                : reliquaryRepository.findBriefByIdIn(set.getContainsList()).stream()
                        .map(this::toReliquary)
                        .toList();
        Map<Integer, Reliquary> reliquaryById = reliquaries.stream()
                .collect(Collectors.toMap(
                        Reliquary::getId, reliquary -> reliquary, (existing, replacement) -> existing));

        ReliquaryStory story = reliquaryStoryRepository.findById(set.getId()).orElse(null);
        List<List<String>> stories = story == null || story.getStories() == null ? List.of() : story.getStories();

        Set<String> hashes = new LinkedHashSet<>();
        if (!affixLevels.isEmpty()) {
            hashes.add(affixLevels.get(0).getNameTextMapHash());
        }
        for (EquipAffix.AffixLevel affixLevel : affixLevels) {
            hashes.add(affixLevel.getDescTextMapHash());
        }
        for (Reliquary reliquary : reliquaries) {
            hashes.add(reliquary.getNameTextMapHash());
            hashes.add(reliquary.getDescTextMapHash());
        }
        Map<String, String> texts = textMapChsUtil.getTexts(hashes);

        ReliquaryBaseInfoDTO dto = new ReliquaryBaseInfoDTO();
        dto.setSetId(set.getId());
        dto.setName(affixLevels.isEmpty() ? null : texts.get(affixLevels.get(0).getNameTextMapHash()));
        dto.setSetAffixNum(set.getSetNeedNum());
        dto.setMaxRankLevel(set.getMaxRankLevel());
        dto.setSetIconName(set.getSetIcon());

        List<String> affixDesc = new ArrayList<>();
        for (EquipAffix.AffixLevel affixLevel : affixLevels) {
            String desc = texts.get(affixLevel.getDescTextMapHash());
            if (desc != null) {
                affixDesc.add(desc);
            }
        }
        dto.setSetAffixDesc(affixDesc);

        List<ReliquaryBaseInfoDTO.Contains> contains = new ArrayList<>();
        if (set.getContainsList() != null) {
            for (Integer reliquaryId : set.getContainsList()) {
                Reliquary reliquary = reliquaryById.get(reliquaryId);
                ReliquaryBaseInfoDTO.Contains item = new ReliquaryBaseInfoDTO.Contains();
                item.setId(reliquaryId);
                item.setName(reliquary == null ? null : texts.get(reliquary.getNameTextMapHash()));
                item.setDesc(reliquary == null ? null : texts.get(reliquary.getDescTextMapHash()));
                item.setIconName(reliquary == null ? null : reliquary.getIcon());
                item.setStory(pickStory(stories, reliquaryId));
                contains.add(item);
            }
        }
        dto.setContains(contains);
        return dto;
    }

    @Override
    public ReliquaryPropRankDTO getReliquaryPropRank() {
        Map<Integer, ReliquaryLevel> levels = reliquaryLevelRepository.findAllById(List.of(1, 2, 3, 4, 5)).stream()
                .collect(Collectors.toMap(ReliquaryLevel::getId, level -> level, (existing, replacement) -> existing));
        List<ReliquaryAffix> affixes = reliquaryAffixRepository.findAll().stream()
                .filter(affix -> affix.getId() != null)
                .sorted(Comparator.comparing(ReliquaryAffix::getId))
                .toList();

        ReliquaryPropRankDTO dto = new ReliquaryPropRankDTO();
        List<ReliquaryPropRankDTO.RankLevel> rankLevels = new ArrayList<>();
        for (int rankLevel = 1; rankLevel <= 5; rankLevel++) {
            ReliquaryPropRankDTO.RankLevel item = new ReliquaryPropRankDTO.RankLevel();
            item.setRankLevel(rankLevel);
            item.setMainProps(buildMainProps(levels.get(rankLevel)));
            item.setAppendProps(buildAppendProps(rankLevel, affixes));
            rankLevels.add(item);
        }
        dto.setRankLevels(rankLevels);
        return dto;
    }

    // 主属性：reliquary_level._id = 星级，按等级顺序取 levels[].addProps 的 propType / value
    private List<ReliquaryPropRankDTO.MainProp> buildMainProps(ReliquaryLevel reliquaryLevel) {
        Map<String, List<Double>> grouped = new LinkedHashMap<>();
        if (reliquaryLevel != null && reliquaryLevel.getLevels() != null) {
            for (ReliquaryLevel.Level level : reliquaryLevel.getLevels()) {
                if (level.getAddProps() == null) {
                    continue;
                }
                for (ReliquaryLevel.AddProp addProp : level.getAddProps()) {
                    if (addProp.getPropType() == null
                            || addProp.getValue() == null
                            || FIRE_SUB_HURT.equals(addProp.getPropType())) {
                        continue;
                    }
                    grouped.computeIfAbsent(addProp.getPropType(), key -> new ArrayList<>())
                            .add(addProp.getValue());
                }
            }
        }
        List<ReliquaryPropRankDTO.MainProp> mainProps = new ArrayList<>();
        grouped.forEach((propType, values) -> {
            ReliquaryPropRankDTO.MainProp mainProp = new ReliquaryPropRankDTO.MainProp();
            mainProp.setPropType(propType);
            mainProp.setValues(values);
            mainProps.add(mainProp);
        });
        return mainProps;
    }

    // 副词条：reliquary_affix.depotId 左起第一位 = 星级，同 propType 的 propValue 按 _id 顺序汇总
    private List<ReliquaryPropRankDTO.AppendProp> buildAppendProps(int rankLevel, List<ReliquaryAffix> affixes) {
        Map<String, List<Double>> grouped = new LinkedHashMap<>();
        for (ReliquaryAffix affix : affixes) {
            if (affix.getDepotId() == null || affix.getPropType() == null || affix.getPropValue() == null) {
                continue;
            }
            String depotId = String.valueOf(affix.getDepotId());
            if (Character.getNumericValue(depotId.charAt(0)) != rankLevel) {
                continue;
            }
            grouped.computeIfAbsent(affix.getPropType(), key -> new ArrayList<>())
                    .add(affix.getPropValue());
        }
        List<ReliquaryPropRankDTO.AppendProp> appendProps = new ArrayList<>();
        grouped.forEach((propType, values) -> {
            ReliquaryPropRankDTO.AppendProp appendProp = new ReliquaryPropRankDTO.AppendProp();
            appendProp.setPropType(propType);
            appendProp.setValues(values);
            appendProps.add(appendProp);
        });
        return appendProps;
    }

    private Reliquary toReliquary(ReliquaryRepository.ReliquaryBriefView view) {
        Reliquary reliquary = new Reliquary();
        reliquary.setId(view.getId());
        reliquary.setNameTextMapHash(view.getNameTextMapHash());
        reliquary.setDescTextMapHash(view.getDescTextMapHash());
        reliquary.setIcon(view.getIcon());
        return reliquary;
    }

    private List<EquipAffix.AffixLevel> sortedAffixLevels(EquipAffix affix) {
        List<EquipAffix.AffixLevel> levels = new ArrayList<>();
        if (affix != null && affix.getLevels() != null) {
            levels.addAll(affix.getLevels());
            levels.sort(Comparator.comparing(
                    EquipAffix.AffixLevel::getLevel, Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return levels;
    }

    private EquipAffix.AffixLevel firstAffixLevel(EquipAffix affix) {
        List<EquipAffix.AffixLevel> levels = sortedAffixLevels(affix);
        return levels.isEmpty() ? null : levels.get(0);
    }

    private List<String> pickStory(List<List<String>> stories, Integer reliquaryId) {
        if (stories.isEmpty()) {
            return new ArrayList<>();
        }
        if (stories.size() == 1) {
            return stories.get(0);
        }
        int index = getStoryIndex(reliquaryId);
        return index < stories.size() ? stories.get(index) : new ArrayList<>();
    }

    // 部件 ID 从左往右第四位数字减一，即对应套装故事中的章节下标（如 47442 -> 第 3 号故事）
    private int getStoryIndex(Integer reliquaryId) {
        String id = String.valueOf(reliquaryId);
        if (id.length() < 4) {
            return 0;
        }
        return Math.max(Character.getNumericValue(id.charAt(3)) - 1, 0);
    }
}
