package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.WeaponBaseInfoDTO;
import com.genshin.irminsul.dto.WeaponList;
import com.genshin.irminsul.entity.*;
import com.genshin.irminsul.repository.*;
import com.genshin.irminsul.service.WeaponService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeaponServiceImpl implements WeaponService {
    private static final int MAX_LEVEL = 100;
    private static final int MAX_PROMOTE_LEVEL = 6;

    private final WeaponRepository weaponRepository;
    private final WeaponCodexRepository weaponCodexRepository;
    private final WeaponPromoteRepository weaponPromoteRepository;
    private final WeaponCurveRepository weaponCurveRepository;
    private final EquipAffixRepository equipAffixRepository;
    private final WeaponStoryRepository weaponStoryRepository;
    private final MaterialRepository materialRepository;
    private final TextMapChsUtil textMapChsUtil;

    // 剧情特殊武器与废案武器
    private static final Set<Integer> EXCLUDED_WEAPON_IDS =
            Set.of(11419, 11420, 11421, 11429, 12304, 13304, 14306, 15306);

    // 曲线表与等级无关，进程内缓存，改库后重启生效
    private volatile Map<Integer, WeaponCurve> weaponCurveCache;

    @Override
    public List<WeaponList.WeaponBrief> getWeaponList(String name, List<String> weaponTypes, List<Integer> ranks) {
        List<WeaponRepository.WeaponBriefView> weapons = weaponRepository.findAllBrief();
        Map<Integer, Integer> sortOrderByWeaponId = new HashMap<>();
        for (WeaponCodexRepository.WeaponCodexBriefView codex : weaponCodexRepository.findAllBrief()) {
            if (codex.getWeaponId() != null && !sortOrderByWeaponId.containsKey(codex.getWeaponId())) {
                sortOrderByWeaponId.put(codex.getWeaponId(), codex.getSortOrder());
            }
        }

        Map<String, String> texts = textMapChsUtil.getTexts(weapons.stream()
                .map(WeaponRepository.WeaponBriefView::getNameTextMapHash)
                .toList());

        String nameFilter = name == null || name.trim().isEmpty() ? null : name.trim();
        List<WeaponList.WeaponBrief> result = new ArrayList<>();
        for (WeaponRepository.WeaponBriefView weapon : weapons) {
            String weaponName = texts.get(weapon.getNameTextMapHash());
            // 测试武器与未上线占位武器不进入列表
            if (weaponName == null || weaponName.startsWith("(test)")) {
                continue;
            }
            if (EXCLUDED_WEAPON_IDS.contains(weapon.getId())) {
                continue;
            }
            if (nameFilter != null && !weaponName.contains(nameFilter)) {
                continue;
            }
            if (weaponTypes != null && !weaponTypes.isEmpty() && !weaponTypes.contains(weapon.getWeaponType())) {
                continue;
            }
            if (ranks != null && !ranks.isEmpty() && !ranks.contains(weapon.getRankLevel())) {
                continue;
            }

            WeaponList.WeaponBrief brief = new WeaponList.WeaponBrief();
            brief.setId(weapon.getId());
            brief.setSortId(sortOrderByWeaponId.get(weapon.getId()));
            brief.setName(weaponName);
            brief.setRank(weapon.getRankLevel());
            brief.setWeaponType(weapon.getWeaponType());
            brief.setGachaCardName(toGachaCard(weapon.getIcon()));
            brief.setIconName(weapon.getIcon());
            result.add(brief);
        }

        result.sort(Comparator.comparing(
                (WeaponList.WeaponBrief brief) -> brief.getSortId() == null ? 0 : brief.getSortId(),
                Comparator.reverseOrder()));
        return result;
    }

    @Override
    public WeaponBaseInfoDTO getWeaponBaseInfo(Integer id) {
        Weapon weapon = weaponRepository.findById(id).orElse(null);
        if (weapon == null) {
            return null;
        }

        WeaponPromote promote = weapon.getWeaponPromoteId() == null
                ? new WeaponPromote()
                : weaponPromoteRepository.findById(weapon.getWeaponPromoteId()).orElse(new WeaponPromote());
        List<WeaponPromote.Level> promoteLevels = promote.getLevels();

        Weapon.WeaponProp baseProp = null;
        Weapon.WeaponProp subProp = null;
        if (weapon.getWeaponProp() != null) {
            for (Weapon.WeaponProp prop : weapon.getWeaponProp()) {
                if (prop == null) {
                    continue;
                }
                if ("FIGHT_PROP_BASE_ATTACK".equals(prop.getPropType())) {
                    if (baseProp == null) {
                        baseProp = prop;
                    }
                } else if (prop.getPropType() != null && subProp == null) {
                    subProp = prop;
                }
            }
            if (baseProp == null && !weapon.getWeaponProp().isEmpty()) {
                baseProp = weapon.getWeaponProp().get(0);
            }
        }

        double[] atkBonus = new double[MAX_PROMOTE_LEVEL + 1];
        Set<Integer> materialIds = new LinkedHashSet<>();
        if (promoteLevels != null) {
            for (WeaponPromote.Level level : promoteLevels) {
                Integer promoteLevel = level.getPromoteLevel();
                if (level.getAddProps() != null) {
                    for (WeaponPromote.AddProp addProp : level.getAddProps()) {
                        if (addProp == null || addProp.getValue() == null) {
                            continue;
                        }
                        if ("FIGHT_PROP_BASE_ATTACK".equals(addProp.getPropType())
                                && promoteLevel != null
                                && promoteLevel >= 0
                                && promoteLevel <= MAX_PROMOTE_LEVEL) {
                            atkBonus[promoteLevel] = addProp.getValue();
                        }
                    }
                }
                if (level.getCostItems() != null) {
                    for (WeaponPromote.CostItem item : level.getCostItems()) {
                        if (item != null && item.getId() != null) {
                            materialIds.add(item.getId());
                        }
                    }
                }
            }
        }

        Integer affixId = null;
        if (weapon.getSkillAffix() != null) {
            for (Integer skillAffix : weapon.getSkillAffix()) {
                if (skillAffix != null && skillAffix != 0) {
                    affixId = skillAffix;
                    break;
                }
            }
        }
        EquipAffix affix =
                affixId == null ? null : equipAffixRepository.findById(affixId).orElse(null);
        List<EquipAffix.AffixLevel> affixLevels = new ArrayList<>();
        if (affix != null && affix.getLevels() != null) {
            affixLevels.addAll(affix.getLevels());
            affixLevels.sort(Comparator.comparing(
                    EquipAffix.AffixLevel::getLevel, Comparator.nullsLast(Comparator.naturalOrder())));
        }

        Map<Integer, MaterialRepository.MaterialBriefView> materials =
                materialRepository.findBriefByIdIn(materialIds).stream()
                        .filter(material -> material.getId() != null)
                        .collect(Collectors.toMap(
                                MaterialRepository.MaterialBriefView::getId,
                                material -> material,
                                (existing, replacement) -> existing));

        Set<String> hashes = new LinkedHashSet<>();
        hashes.add(weapon.getNameTextMapHash());
        hashes.add(weapon.getDescTextMapHash());
        for (MaterialRepository.MaterialBriefView material : materials.values()) {
            hashes.add(material.getNameTextMapHash());
        }
        for (EquipAffix.AffixLevel affixLevel : affixLevels) {
            hashes.add(affixLevel.getNameTextMapHash());
            hashes.add(affixLevel.getDescTextMapHash());
        }
        Map<String, String> texts = textMapChsUtil.getTexts(hashes);

        Map<Integer, String> materialNames = new HashMap<>();
        materials.forEach(
                (materialId, material) -> materialNames.put(materialId, texts.get(material.getNameTextMapHash())));

        WeaponBaseInfoDTO dto = new WeaponBaseInfoDTO();
        dto.setId(weapon.getId());
        dto.setName(texts.get(weapon.getNameTextMapHash()));
        dto.setDesc(texts.get(weapon.getDescTextMapHash()));
        dto.setWeaponType(weapon.getWeaponType());
        dto.setRankLevel(weapon.getRankLevel());
        dto.setIcon(weapon.getIcon());
        dto.setAwakenIcon(weapon.getAwakenIcon());

        double baseInit = baseProp == null || baseProp.getInitValue() == null ? 0.0 : baseProp.getInitValue();
        String baseCurve = baseProp == null ? null : baseProp.getType();
        double subInit = subProp == null || subProp.getInitValue() == null ? 0.0 : subProp.getInitValue();
        String subCurve = subProp == null ? null : subProp.getType();

        Map<Integer, WeaponCurve> curves = weaponCurves();
        List<Double> atk = new ArrayList<>(MAX_LEVEL);
        List<Double> subStatValue = new ArrayList<>(MAX_LEVEL);
        for (int level = 1; level <= MAX_LEVEL; level++) {
            WeaponCurve curve = curves.get(level);
            int promoteLevel = toPromoteLevel(level);
            atk.add(baseInit * curveValue(curve, baseCurve) + atkBonus[promoteLevel]);
            if (subProp != null) {
                subStatValue.add(subInit * curveValue(curve, subCurve));
            }
        }

        dto.setAtk(atk);
        dto.setSubStatName(subProp == null ? null : translateStat(subProp.getPropType()));
        dto.setSubStatValue(subStatValue);

        List<WeaponBaseInfoDTO.Material> materialList = new ArrayList<>();
        if (promoteLevels != null) {
            for (WeaponPromote.Level level : promoteLevels) {
                Integer promoteLevel = level.getPromoteLevel();
                if (promoteLevel == null || promoteLevel < 1) {
                    continue;
                }
                WeaponBaseInfoDTO.Material material = new WeaponBaseInfoDTO.Material();
                material.setPromoteLevel(promoteLevel);
                material.setMaxLevel(level.getUnlockMaxLevel());
                material.setMora(level.getCoinCost());
                List<WeaponBaseInfoDTO.CostItem> costItems = new ArrayList<>();
                if (level.getCostItems() != null) {
                    for (WeaponPromote.CostItem item : level.getCostItems()) {
                        if (item == null || item.getId() == null) {
                            continue;
                        }
                        WeaponBaseInfoDTO.CostItem costItem = new WeaponBaseInfoDTO.CostItem();
                        costItem.setId(item.getId());
                        costItem.setName(materialNames.get(item.getId()));
                        costItem.setCount(item.getCount());
                        MaterialRepository.MaterialBriefView view = materials.get(item.getId());
                        costItem.setIconName(view == null ? null : view.getIcon());
                        costItems.add(costItem);
                    }
                }
                material.setCostItems(costItems);
                materialList.add(material);
            }
        }
        dto.setMaterials(materialList);

        List<String> affixDesc = new ArrayList<>();
        if (!affixLevels.isEmpty()) {
            dto.setAffixName(texts.get(affixLevels.get(0).getNameTextMapHash()));
            for (EquipAffix.AffixLevel affixLevel : affixLevels) {
                String desc = texts.get(affixLevel.getDescTextMapHash());
                if (desc != null) {
                    affixDesc.add(desc);
                }
            }
        }
        dto.setAffixDesc(affixDesc);

        WeaponStory story = weaponStoryRepository.findById(id).orElse(null);
        dto.setStories(story == null || story.getStories() == null ? new ArrayList<>() : story.getStories());
        return dto;
    }

    private Map<Integer, WeaponCurve> weaponCurves() {
        Map<Integer, WeaponCurve> curves = weaponCurveCache;
        if (curves == null) {
            synchronized (this) {
                curves = weaponCurveCache;
                if (curves == null) {
                    curves = weaponCurveRepository.findAll().stream()
                            .filter(curve -> curve.getLevel() != null)
                            .collect(Collectors.toMap(
                                    WeaponCurve::getLevel, curve -> curve, (existing, replacement) -> existing));
                    weaponCurveCache = curves;
                }
            }
        }
        return curves;
    }

    private static double curveValue(WeaponCurve curve, String type) {
        if (curve == null || curve.getCurves() == null || type == null) {
            return 0.0;
        }
        Double value = curve.getCurves().get(type);
        return value == null ? 0.0 : value;
    }

    private static int toPromoteLevel(int level) {
        if (level >= 80) return 6;
        if (level >= 70) return 5;
        if (level >= 60) return 4;
        if (level >= 50) return 3;
        if (level >= 40) return 2;
        if (level >= 20) return 1;
        return 0;
    }

    private static String toGachaCard(String icon) {
        if (icon == null) {
            return null;
        }
        String suffix = icon.startsWith("UI_EquipIcon_") ? icon.substring("UI_EquipIcon_".length()) : icon;
        return "UI_Gacha_EquipIcon_" + suffix;
    }

    private static String translateStat(String stat) {
        if (stat == null) {
            return "未知";
        }
        return switch (stat) {
            case "FIGHT_PROP_HP_PERCENT" -> "生命值";
            case "FIGHT_PROP_ATTACK_PERCENT" -> "攻击力";
            case "FIGHT_PROP_DEFENSE_PERCENT" -> "防御力";
            case "FIGHT_PROP_ELEMENT_MASTERY" -> "元素精通";
            case "FIGHT_PROP_CHARGE_EFFICIENCY" -> "元素充能效率";
            case "FIGHT_PROP_CRITICAL" -> "暴击率";
            case "FIGHT_PROP_CRITICAL_HURT" -> "暴击伤害";
            case "FIGHT_PROP_HEAL_ADD" -> "治疗加成";
            case "FIGHT_PROP_FIRE_ADD_HURT" -> "火元素伤害加成";
            case "FIGHT_PROP_WATER_ADD_HURT" -> "水元素伤害加成";
            case "FIGHT_PROP_ICE_ADD_HURT" -> "冰元素伤害加成";
            case "FIGHT_PROP_ELEC_ADD_HURT" -> "雷元素伤害加成";
            case "FIGHT_PROP_WIND_ADD_HURT" -> "风元素伤害加成";
            case "FIGHT_PROP_ROCK_ADD_HURT" -> "岩元素伤害加成";
            case "FIGHT_PROP_GRASS_ADD_HURT" -> "草元素伤害加成";
            case "FIGHT_PROP_PHYSICAL_ADD_HURT" -> "物理伤害加成";
            default -> stat;
        };
    }
}
