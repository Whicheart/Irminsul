package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.reliquary.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.reliquary.ReliquaryPropRankDTO;
import com.genshin.irminsul.dto.reliquary.ReliquarySetList;
import com.genshin.irminsul.entity.*;
import com.genshin.irminsul.entity.reliquary.*;
import com.genshin.irminsul.repository.*;
import com.genshin.irminsul.repository.reliquary.*;
import com.genshin.irminsul.service.ReliquaryService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 圣遗物业务实现
 *
 * <p>套装数据来自 reliquary_set 与 equip_affix；星级 1~5 的主属性取自
 * reliquary_level，副词条按 depotId 首位数字匹配星级
 */
@Service
@RequiredArgsConstructor
public class ReliquaryServiceImpl implements ReliquaryService {

    // 15000 号套装缺少 equipAffixId 与文本，15004/15012 无可用故事
    private static final Set<Integer> EXCLUDED_SET_IDS = Set.of(15000, 15004, 15012);

    // 火抗未实装
    private static final String FIRE_SUB_HURT = "FIGHT_PROP_FIRE_SUB_HURT";

    // 圣遗物套装仓储
    private final ReliquarySetRepository reliquarySetRepository;
    // 单件圣遗物仓储
    private final ReliquaryRepository reliquaryRepository;
    // 圣遗物故事仓储
    private final ReliquaryStoryRepository reliquaryStoryRepository;
    // 套装效果仓储
    private final EquipAffixRepository equipAffixRepository;
    // 圣遗物等级主属性仓储
    private final ReliquaryLevelRepository reliquaryLevelRepository;
    // 圣遗物副词条仓储
    private final ReliquaryAffixRepository reliquaryAffixRepository;
    // 文本哈希解析工具
    private final TextMapChsUtil textMapChsUtil;

    /**
     * 查询圣遗物套装列表
     *
     * <p>以套装配置为源，按最高星级与 ID 倒序，名称取套装效果第一档
     *
     * @param name  名称关键词，可选
     * @param ranks 星级筛选，可选
     * @return 套装简要列表
     */
    @Override
    public List<ReliquarySetList.RelicSetBrief> getReliquaryList(String name, List<Integer> ranks) {
        String nameFilter = name == null ? null : name.trim();
        if (nameFilter != null && nameFilter.isEmpty()) {
            nameFilter = null;
        }
        List<Integer> rankFilter = ranks == null || ranks.isEmpty() ? null : ranks;

        List<ReliquarySet> sets = reliquarySetRepository.findAll().stream()
                .filter(set -> set.getId() != null && !EXCLUDED_SET_IDS.contains(set.getId()))
                .filter(set -> rankFilter == null || rankFilter.contains(set.getMaxRankLevel()))
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
            EquipAffix affix = set.getEquipAffixId() == null ? null : affixById.get(set.getEquipAffixId());
            EquipAffix.AffixLevel first = affix == null ? null : firstAffixLevel(affix);
            String setName = first == null ? null : texts.get(first.getNameTextMapHash());
            if (nameFilter != null && (setName == null || !setName.contains(nameFilter))) {
                continue;
            }
            ReliquarySetList.RelicSetBrief brief = new ReliquarySetList.RelicSetBrief();
            brief.setSetId(set.getId());
            brief.setName(setName);
            brief.setMaxRankLevel(set.getMaxRankLevel());
            brief.setSetIconName(set.getSetIcon());
            result.add(brief);
        }
        return result;
    }

    /**
     * 组装圣遗物套装基础信息
     *
     * <p>包含套装效果、部件列表与故事；套装不存在时返回 null
     *
     * @param id 套装 ID
     * @return 套装详情，不存在时为 null
     */
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

    /**
     * 查询 1~5 星圣遗物的主属性与副词条范围
     *
     * @return 按星级组织的主属性与副词条数据
     */
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

    /**
     * 把投影视图转换为圣遗物实体，只填充列表展示所需字段
     */
    private Reliquary toReliquary(ReliquaryRepository.ReliquaryBriefView view) {
        Reliquary reliquary = new Reliquary();
        reliquary.setId(view.getId());
        reliquary.setNameTextMapHash(view.getNameTextMapHash());
        reliquary.setDescTextMapHash(view.getDescTextMapHash());
        reliquary.setIcon(view.getIcon());
        return reliquary;
    }

    /**
     * 取套装效果的各档配置，按档位升序；空值返回空列表
     */
    private List<EquipAffix.AffixLevel> sortedAffixLevels(EquipAffix affix) {
        List<EquipAffix.AffixLevel> levels = new ArrayList<>();
        if (affix != null && affix.getLevels() != null) {
            levels.addAll(affix.getLevels());
            levels.sort(Comparator.comparing(
                    EquipAffix.AffixLevel::getLevel, Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return levels;
    }

    /**
     * 取套装效果的第一档配置，可能返回 null
     */
    private EquipAffix.AffixLevel firstAffixLevel(EquipAffix affix) {
        List<EquipAffix.AffixLevel> levels = sortedAffixLevels(affix);
        return levels.isEmpty() ? null : levels.get(0);
    }

    /**
     * 按部件选择套装故事章节：单故事直接返回，多故事按部件 ID 第 4 位数字定位
     */
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
