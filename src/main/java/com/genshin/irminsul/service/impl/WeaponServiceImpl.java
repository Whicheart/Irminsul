package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.WeaponBaseInfoDTO;
import com.genshin.irminsul.dto.WeaponList;
import com.genshin.irminsul.entity.EquipAffix;
import com.genshin.irminsul.entity.Weapon;
import com.genshin.irminsul.entity.WeaponCodex;
import com.genshin.irminsul.entity.WeaponCurve;
import com.genshin.irminsul.entity.WeaponPromote;
import com.genshin.irminsul.entity.WeaponStory;
import com.genshin.irminsul.repository.EquipAffixRepository;
import com.genshin.irminsul.repository.MaterialRepository;
import com.genshin.irminsul.repository.WeaponCodexRepository;
import com.genshin.irminsul.repository.WeaponCurveRepository;
import com.genshin.irminsul.repository.WeaponPromoteRepository;
import com.genshin.irminsul.repository.WeaponRepository;
import com.genshin.irminsul.repository.WeaponStoryRepository;
import com.genshin.irminsul.service.WeaponService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeaponServiceImpl implements WeaponService {
    private final WeaponRepository weaponRepository;
    private final WeaponCodexRepository weaponCodexRepository;
    private final WeaponPromoteRepository weaponPromoteRepository;
    private final WeaponCurveRepository weaponCurveRepository;
    private final EquipAffixRepository equipAffixRepository;
    private final WeaponStoryRepository weaponStoryRepository;
    private final MaterialRepository materialRepository;
    private final TextMapChsUtil textMapChsUtil;

    @Override
    public List<WeaponList.WeaponBrief> getWeaponList(String name, List<String> weaponTypes, List<Integer> ranks) {
        List<Weapon> weapons = weaponRepository.findAll();
        Map<Integer, WeaponCodex> codexMap = weaponCodexRepository.findAll().stream()
                .filter(codex -> codex.getWeaponId() != null)
                .collect(Collectors.toMap(
                        WeaponCodex::getWeaponId, codex -> codex, (existing, replacement) -> existing));

        List<WeaponList.WeaponBrief> result = new ArrayList<>();
        for (Weapon weapon : weapons) {
            String weaponName = textMapChsUtil.getText(weapon.getNameTextMapHash());
            // 测试武器与未上线占位武器没有正式名称，不进入列表
            if (weaponName == null || weaponName.startsWith("(test)")) continue;

            if (name != null && !name.isEmpty() && !weaponName.contains(name)) continue;
            if (weaponTypes != null && !weaponTypes.isEmpty() && !weaponTypes.contains(weapon.getWeaponType()))
                continue;
            if (ranks != null && !ranks.isEmpty() && !ranks.contains(weapon.getRankLevel())) continue;

            WeaponCodex codex = codexMap.get(weapon.getId());
            WeaponList.WeaponBrief brief = new WeaponList.WeaponBrief();
            brief.setId(weapon.getId());
            brief.setName(weaponName);
            brief.setSortId(codex != null ? codex.getSortOrder() : null);
            brief.setRank(weapon.getRankLevel());
            brief.setWeaponType(weapon.getWeaponType());
            brief.setGachaIconName(toGachaIcon(weapon.getIcon()));
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
        if (weapon == null) return null;

        WeaponBaseInfoDTO weaponBaseInfoDTO = new WeaponBaseInfoDTO();
        weaponBaseInfoDTO.setId(weapon.getId());
        weaponBaseInfoDTO.setName(textMapChsUtil.getText(weapon.getNameTextMapHash()));
        weaponBaseInfoDTO.setDesc(textMapChsUtil.getText(weapon.getDescTextMapHash()));
        weaponBaseInfoDTO.setWeaponType(translateWeapon(weapon.getWeaponType()));
        weaponBaseInfoDTO.setRankLevel(weapon.getRankLevel());
        weaponBaseInfoDTO.setIcon(weapon.getIcon());
        weaponBaseInfoDTO.setAwakenIcon(weapon.getAwakenIcon());

        // ==========================================
        // 基础攻击与副属性：区分两条成长曲线
        // ==========================================
        List<Weapon.WeaponProp> props = weapon.getWeaponProp();
        Weapon.WeaponProp baseProp = null;
        Weapon.WeaponProp subProp = null;
        if (props != null) {
            for (Weapon.WeaponProp prop : props) {
                if (prop == null) continue;
                if ("FIGHT_PROP_BASE_ATTACK".equals(prop.getPropType())) {
                    if (baseProp == null) baseProp = prop;
                } else if (prop.getPropType() != null && subProp == null) {
                    subProp = prop;
                }
            }
            if (baseProp == null && !props.isEmpty()) baseProp = props.get(0);
        }

        // ==========================================
        // 突破加成：每个突破等级对基础攻击的固定加成
        // ==========================================
        WeaponPromote weaponPromote = weapon.getWeaponPromoteId() == null
                ? new WeaponPromote()
                : weaponPromoteRepository.findById(weapon.getWeaponPromoteId()).orElse(new WeaponPromote());
        List<WeaponPromote.PromoteLevel> promoteLevels = weaponPromote.getLevels();

        Map<Integer, Double> atkBonusMap = new HashMap<>();
        for (int i = 0; i <= 6; i++) {
            atkBonusMap.put(i, 0.0);
        }
        if (promoteLevels != null) {
            for (WeaponPromote.PromoteLevel promoteLevel : promoteLevels) {
                if (promoteLevel.getAddProps() == null) continue;
                for (WeaponPromote.AddProp addProp : promoteLevel.getAddProps()) {
                    if (addProp == null || addProp.getValue() == null) continue;
                    if ("FIGHT_PROP_BASE_ATTACK".equals(addProp.getPropType())) {
                        atkBonusMap.put(promoteLevel.getPromoteLevel(), addProp.getValue());
                    }
                }
            }
        }

        // ==========================================
        // 1~100 级成长曲线
        // ==========================================
        Map<Integer, WeaponCurve> curveMap = weaponCurveRepository.findAll().stream()
                .filter(curve -> curve.getLevel() != null)
                .collect(Collectors.toMap(WeaponCurve::getLevel, curve -> curve, (existing, replacement) -> existing));

        double baseInit = baseProp != null && baseProp.getInitValue() != null ? baseProp.getInitValue() : 0.0;
        String baseCurveType = baseProp != null ? baseProp.getType() : null;
        double subInit = subProp != null && subProp.getInitValue() != null ? subProp.getInitValue() : 0.0;
        String subCurveType = subProp != null ? subProp.getType() : null;

        List<Double> atkList = new ArrayList<>();
        List<Double> subStatList = new ArrayList<>();
        for (int level = 1; level <= curveMap.size(); level++) {
            WeaponCurve curve = curveMap.get(level);
            int promoteLevel = toPromoteLevel(level);

            double atk = 0.0;
            if (curve != null && curve.getCurves() != null) {
                Double baseMulti = curve.getCurves().get(baseCurveType);
                if (baseMulti != null) atk = baseInit * baseMulti;
            }
            atk += atkBonusMap.getOrDefault(promoteLevel, 0.0);
            atkList.add(atk);

            if (subProp != null) {
                double subStat = 0.0;
                if (curve != null && curve.getCurves() != null) {
                    Double subMulti = curve.getCurves().get(subCurveType);
                    if (subMulti != null) subStat = subInit * subMulti;
                }
                subStatList.add(subStat);
            }
        }

        weaponBaseInfoDTO.setAtk(atkList);
        weaponBaseInfoDTO.setSubStatName(subProp != null ? translateStat(subProp.getPropType()) : null);
        weaponBaseInfoDTO.setSubStatValue(subStatList);

        // ==========================================
        // 突破材料
        // ==========================================
        List<Integer> materialIds = new ArrayList<>();
        if (promoteLevels != null) {
            for (WeaponPromote.PromoteLevel promoteLevel : promoteLevels) {
                if (promoteLevel.getCostItems() == null) continue;
                for (WeaponPromote.CostItem item : promoteLevel.getCostItems()) {
                    if (item != null && item.getId() != null) materialIds.add(item.getId());
                }
            }
        }
        Map<Integer, String> materialNameMap = new HashMap<>();
        if (!materialIds.isEmpty()) {
            materialRepository
                    .findAllById(materialIds)
                    .forEach(material -> materialNameMap.put(
                            material.getId(), textMapChsUtil.getText(material.getNameTextMapHash())));
        }

        List<WeaponBaseInfoDTO.Material> materials = new ArrayList<>();
        if (promoteLevels != null) {
            for (WeaponPromote.PromoteLevel promoteLevel : promoteLevels) {
                // levels 开头有 0 级占位，没有消耗
                if (promoteLevel.getPromoteLevel() == null || promoteLevel.getPromoteLevel() == 0) continue;
                WeaponBaseInfoDTO.Material material = new WeaponBaseInfoDTO.Material();
                material.setPromoteLevel(promoteLevel.getPromoteLevel());
                material.setMaxLevel(promoteLevel.getUnlockMaxLevel());
                material.setMora(promoteLevel.getCoinCost());

                List<WeaponBaseInfoDTO.CostItem> costItems = new ArrayList<>();
                if (promoteLevel.getCostItems() != null) {
                    for (WeaponPromote.CostItem item : promoteLevel.getCostItems()) {
                        if (item == null || item.getId() == null) continue;
                        WeaponBaseInfoDTO.CostItem costItem = new WeaponBaseInfoDTO.CostItem();
                        costItem.setId(item.getId());
                        costItem.setName(materialNameMap.get(item.getId()));
                        costItem.setCount(item.getCount());
                        costItems.add(costItem);
                    }
                }
                material.setCostItems(costItems);
                materials.add(material);
            }
        }
        weaponBaseInfoDTO.setMaterials(materials);

        // ==========================================
        // 武器特效：skillAffix 指向 equip_affix，1~5 精炼
        // ==========================================
        Integer affixId = null;
        if (weapon.getSkillAffix() != null) {
            for (Integer skillAffix : weapon.getSkillAffix()) {
                if (skillAffix != null && skillAffix != 0) {
                    affixId = skillAffix;
                    break;
                }
            }
        }

        List<String> affixDesc = new ArrayList<>();
        if (affixId != null) {
            EquipAffix equipAffix = equipAffixRepository.findById(affixId).orElse(null);
            if (equipAffix != null && equipAffix.getLevels() != null) {
                List<EquipAffix.AffixLevel> affixLevels = new ArrayList<>(equipAffix.getLevels());
                affixLevels.sort(Comparator.comparing(EquipAffix.AffixLevel::getLevel));
                if (!affixLevels.isEmpty()) {
                    weaponBaseInfoDTO.setAffixName(
                            textMapChsUtil.getText(affixLevels.get(0).getNameTextMapHash()));
                }
                for (EquipAffix.AffixLevel affixLevel : affixLevels) {
                    String desc = textMapChsUtil.getText(affixLevel.getDescTextMapHash());
                    if (desc != null) affixDesc.add(desc);
                }
            }
        }
        weaponBaseInfoDTO.setAffixDesc(affixDesc);

        // ==========================================
        // 武器故事：按武器 ID 关联，每章由多行文本组成
        // ==========================================
        List<WeaponBaseInfoDTO.Story> stories = new ArrayList<>();
        WeaponStory weaponStory = weaponStoryRepository.findById(id).orElse(null);
        if (weaponStory != null && weaponStory.getStories() != null) {
            for (List<String> storyLines : weaponStory.getStories()) {
                WeaponBaseInfoDTO.Story story = new WeaponBaseInfoDTO.Story();
                story.setStory(storyLines);
                stories.add(story);
            }
        }
        weaponBaseInfoDTO.setStories(stories);

        return weaponBaseInfoDTO;
    }

    private int toPromoteLevel(int level) {
        if (level >= 80) return 6;
        if (level >= 70) return 5;
        if (level >= 60) return 4;
        if (level >= 50) return 3;
        if (level >= 40) return 2;
        if (level >= 20) return 1;
        return 0;
    }

    private String toGachaIcon(String icon) {
        if (icon == null) return null;
        if (icon.startsWith("UI_EquipIcon_")) {
            return "UI_Gacha_EquipIcon_" + icon.substring("UI_EquipIcon_".length());
        }
        return icon + "_Gacha";
    }

    private String translateWeapon(String weapon) {
        if (weapon == null) return "未知";
        return switch (weapon) {
            case "WEAPON_SWORD_ONE_HAND" -> "单手剑";
            case "WEAPON_CLAYMORE" -> "双手剑";
            case "WEAPON_POLE" -> "长柄武器";
            case "WEAPON_BOW" -> "弓";
            case "WEAPON_CATALYST" -> "法器";
            case "WEAPON_CROSSBOW" -> "枪";
            default -> weapon;
        };
    }

    private String translateStat(String stat) {
        if (stat == null) return "未知";
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
