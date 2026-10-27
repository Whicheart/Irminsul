package com.genshin.lunar.service.impl;

import com.genshin.lunar.dto.*;
import com.genshin.lunar.entity.*;
import com.genshin.lunar.repository.*;
import com.genshin.lunar.service.AvatarService;
import com.genshin.lunar.util.TextMapChsUtil;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final AvatarCodexRepository avatarCodexRepository;
    private final FettersRepository fettersRepository;
    private final FetterInfoRepository fetterInfoRepository;
    private final FetterStoryRepository fetterStoryRepository;
    private final TextMapChsUtil textMapChsUtil;
    private final AvatarCurveRepository avatarCurveRepository;
    private final AvatarPromoteRepository avatarPromoteRepository;
    private final MaterialRepository materialRepository;
    private final AvatarCostumeRepository avatarCostumeRepository;
    private final AvatarSkillDepotRepository avatarSkillDepotRepository;
    private final AvatarSkillRepository avatarSkillRepository;
    private final AvatarTalentRepository avatarTalentRepository;
    private final ProudSkillRepository proudSkillRepository;

    private static final Set<String> IGNORE_AVATARS = Set.of("旅行者", "奇偶·男性", "奇偶·女性");
    private static final Set<Integer> IGNORE_SKILLS = Set.of(30506, 11076);

    @Override
    public List<AvatarList.AvatarBrief> getAvatarList(
            String name, List<String> elements, List<String> weaponTypes, List<String> qualityTypes) {
        List<Avatar> avatars = avatarRepository.findAll();
        List<AvatarCodex> codexes = avatarCodexRepository.findAll();
        List<FetterInfo> infos = fetterInfoRepository.findAll();

        Map<Integer, AvatarCodex> codexMap = new HashMap<>();
        for (AvatarCodex codex : codexes) {
            codexMap.put(codex.getId(), codex);
        }

        Map<Integer, FetterInfo> infoMap = new HashMap<>();
        for (FetterInfo info : infos) {
            infoMap.put(info.getId(), info);
        }

        List<AvatarList.AvatarBrief> result = new ArrayList<>();

        for (Avatar avatar : avatars) {
            String currentName = textMapChsUtil.getText(avatar.getNameTextMapHash());
            if (avatar.getId() < 10000002 || avatar.getId() > 10000899) continue;
            if (IGNORE_AVATARS.contains(currentName)) continue;

            AvatarList.AvatarBrief brief = new AvatarList.AvatarBrief();

            brief.setId(avatar.getId());
            brief.setName(textMapChsUtil.getText(avatar.getNameTextMapHash()));
            brief.setQualityType(avatar.getQualityType());
            brief.setWeaponType(avatar.getWeaponType());
            brief.setGachaIconName(avatar.getIconName() + "_Gacha");
            //            brief.setQualityType(translateQuality(avatar.getQualityType()));
            //            brief.setWeaponType(translateWeapon(avatar.getWeaponType()));

            AvatarCodex codex = codexMap.get(avatar.getId());
            if (codex != null) {
                brief.setSortId(codex.getSortId());
            }

            FetterInfo info = infoMap.get(avatar.getId());
            if (info != null) {
                brief.setElement(translateElement(textMapChsUtil.getText(info.getAvatarVisionBeforTextMapHash())));
            }

            result.add(brief);
        }

        result.sort((a, b) -> {
            Integer s1 = a.getSortId() == null ? -1 : a.getSortId();
            Integer s2 = b.getSortId() == null ? -1 : b.getSortId();
            return s2.compareTo(s1);
        });

        if (name != null && !name.trim().isEmpty())
            result = result.stream()
                    .filter(a -> a.getName() != null && a.getName().contains(name.trim()))
                    .collect(Collectors.toList());
        if (elements != null && !elements.isEmpty())
            result = result.stream()
                    .filter(a -> elements.contains(a.getElement()))
                    .collect(Collectors.toList());
        if (weaponTypes != null && !weaponTypes.isEmpty())
            result = result.stream()
                    .filter(a -> weaponTypes.contains(a.getWeaponType()))
                    .collect(Collectors.toList());
        if (qualityTypes != null && !qualityTypes.isEmpty())
            result = result.stream()
                    .filter(a -> qualityTypes.contains(a.getQualityType()))
                    .collect(Collectors.toList());

        return result;
    }

    @Override
    public AvatarBaseInfoDTO getAvatarBaseInfo(Integer id) {
        // ==========================================
        // 前期准备：查主表、关联表
        // ==========================================
        Avatar avatar = avatarRepository.findById(id).orElse(null);
        if (avatar == null) return null;
        Integer avatarPromoteId = avatar.getAvatarPromoteId();

        AvatarBaseInfoDTO avatarBaseInfoDTO = new AvatarBaseInfoDTO();
        AvatarCodex avatarCodex = avatarCodexRepository.findById(id).orElse(new AvatarCodex());
        AvatarPromote avatarPromote =
                avatarPromoteRepository.findById(avatarPromoteId).orElse(new AvatarPromote());
        FetterInfo fetterInfo = fetterInfoRepository.findById(id).orElse(new FetterInfo());
        List<AvatarCurve> avatarCurveList = avatarCurveRepository.findAll();

        Map<Integer, AvatarCurve> curveMap =
                avatarCurveList.stream().collect(Collectors.toMap(AvatarCurve::getLevel, c -> c));

        String defaultPower = "神之眼";

        // ==========================================
        // 基础文本字段组装（使用 textMapChsUtil 翻译 Hash）
        // ==========================================
        avatarBaseInfoDTO.setId(avatar.getId());
        avatarBaseInfoDTO.setName(textMapChsUtil.getText(avatar.getNameTextMapHash()));
        avatarBaseInfoDTO.setTitle(textMapChsUtil.getText(fetterInfo.getAvatarTitleTextMapHash()));
        avatarBaseInfoDTO.setDesc(textMapChsUtil.getText(avatar.getDescTextMapHash()));
        avatarBaseInfoDTO.setQualityType(translateQuality(avatar.getQualityType()));
        avatarBaseInfoDTO.setWeaponType(translateWeapon(avatar.getWeaponType()));
        avatarBaseInfoDTO.setReleaseTime(avatarCodex.getBeginTime());

        // ==========================================
        // 嵌套对象组装（阵营、生日、命座、力量、元素、CV）
        // ==========================================
        List<String> affiliation = new ArrayList<>();
        String aff1 = textMapChsUtil.getText(fetterInfo.getAvatarNativeTextMapHash());
        String aff2 = textMapChsUtil.getText(fetterInfo.getAvatarNative2TextMapHash());
        if (aff1 != null && !aff1.isEmpty()) affiliation.add(aff1);
        if (aff2 != null && !aff2.isEmpty()) affiliation.add(aff2);
        avatarBaseInfoDTO.setAffiliation(affiliation);

        AvatarBaseInfoDTO.Birth birth = new AvatarBaseInfoDTO.Birth();
        birth.setMonth(fetterInfo.getInfoBirthMonth());
        birth.setDay(fetterInfo.getInfoBirthDay());
        avatarBaseInfoDTO.setBirth(birth);

        AvatarBaseInfoDTO.Constellation constellation = new AvatarBaseInfoDTO.Constellation();
        constellation.setBefor(textMapChsUtil.getText(fetterInfo.getAvatarConstellationBeforTextMapHash()));
        constellation.setAfter(textMapChsUtil.getText(fetterInfo.getAvatarConstellationAfterTextMapHash()));
        avatarBaseInfoDTO.setConstellation(constellation);

        AvatarBaseInfoDTO.Power power = new AvatarBaseInfoDTO.Power();
        power.setBefor(textMapChsUtil.getText(fetterInfo.getAvatarPowerBeforTextMapHash()));
        power.setAfter(textMapChsUtil.getText(fetterInfo.getAvatarPowerAfterTextMapHash()));
        if (power.getBefor() == null) power.setBefor(defaultPower);
        avatarBaseInfoDTO.setPower(power);

        AvatarBaseInfoDTO.Element element = new AvatarBaseInfoDTO.Element();
        element.setBefor(textMapChsUtil.getText(fetterInfo.getAvatarVisionBeforTextMapHash()));
        element.setAfter(textMapChsUtil.getText(fetterInfo.getAvatarVisionAfterTextMapHash()));
        avatarBaseInfoDTO.setElement(element);

        AvatarBaseInfoDTO.Cv cv = new AvatarBaseInfoDTO.Cv();
        cv.setCn(textMapChsUtil.getText(fetterInfo.getCvChineseTextMapHash()));
        cv.setEn(textMapChsUtil.getText(fetterInfo.getCvEnglishTextMapHash()));
        cv.setJp(textMapChsUtil.getText(fetterInfo.getCvJapaneseTextMapHash()));
        cv.setKr(textMapChsUtil.getText(fetterInfo.getCvKoreanTextMapHash()));
        avatarBaseInfoDTO.setCv(cv);

        // ==========================================
        // 基础三维、元素精通与突破加成计算
        // ==========================================
        List<AvatarPromote.PromoteLevel> promoteLevels = avatarPromote.getLevels();
        List<Avatar.PropGrowCurve> propGrowCurves = avatar.getPropGrowCurves();
        List<Double> hpList = new ArrayList<>();
        List<Double> atkList = new ArrayList<>();
        List<Double> defList = new ArrayList<>();
        List<Double> propList = new ArrayList<>();

        String hpCurveType = null;
        String atkCurveType = null;
        String defCurveType = null;
        if (propGrowCurves != null) {
            for (Avatar.PropGrowCurve curve : propGrowCurves) {
                if ("FIGHT_PROP_BASE_HP".equals(curve.getType())) hpCurveType = curve.getGrowCurve();
                if ("FIGHT_PROP_BASE_ATTACK".equals(curve.getType())) atkCurveType = curve.getGrowCurve();
                if ("FIGHT_PROP_BASE_DEFENSE".equals(curve.getType())) defCurveType = curve.getGrowCurve();
            }
        }

        Map<Integer, Double> hpBonusMap = new HashMap<>();
        Map<Integer, Double> atkBonusMap = new HashMap<>();
        Map<Integer, Double> defBonusMap = new HashMap<>();
        Map<Integer, Double> propBonusMap = new HashMap<>();
        String ascStatName = null;

        for (int i = 0; i <= 6; i++) {
            hpBonusMap.put(i, 0.0);
            atkBonusMap.put(i, 0.0);
            defBonusMap.put(i, 0.0);
            propBonusMap.put(i, 0.0);
        }

        if (promoteLevels != null) {
            for (AvatarPromote.PromoteLevel lvl : promoteLevels) {
                int pLevel = lvl.getPromoteLevel();
                if (lvl.getAddProps() != null) {
                    for (AvatarPromote.AddProp prop : lvl.getAddProps()) {
                        Double val = prop.getValue() != null ? prop.getValue() : 0.0;
                        if ("FIGHT_PROP_BASE_HP".equals(prop.getPropType())) {
                            hpBonusMap.put(pLevel, val);
                        } else if ("FIGHT_PROP_BASE_ATTACK".equals(prop.getPropType())) {
                            atkBonusMap.put(pLevel, val);
                        } else if ("FIGHT_PROP_BASE_DEFENSE".equals(prop.getPropType())) {
                            defBonusMap.put(pLevel, val);
                        } else if (prop.getPropType() != null
                                && !prop.getPropType().isEmpty()) {
                            ascStatName = prop.getPropType();
                            propBonusMap.put(pLevel, val);
                        }
                    }
                }
            }
        }

        for (int level = 1; level <= curveMap.size(); level++) {
            AvatarCurve curve = curveMap.get(level);
            if (curve != null && curve.getCurves() != null) {
                Double hpMulti = curve.getCurves().get(hpCurveType);
                Double atkMulti = curve.getCurves().get(atkCurveType);
                Double defMulti = curve.getCurves().get(defCurveType);

                int currentPromoteLevel = 0;
                if (level >= 80) currentPromoteLevel = 6;
                else if (level >= 70) currentPromoteLevel = 5;
                else if (level >= 60) currentPromoteLevel = 4;
                else if (level >= 50) currentPromoteLevel = 3;
                else if (level >= 40) currentPromoteLevel = 2;
                else if (level >= 20) currentPromoteLevel = 1;

                Double hpBonus = hpBonusMap.get(currentPromoteLevel);
                Double atkBonus = atkBonusMap.get(currentPromoteLevel);
                Double defBonus = defBonusMap.get(currentPromoteLevel);
                Double propBonus = propBonusMap.get(currentPromoteLevel);

                hpList.add(hpMulti != null ? avatar.getHpBase() * hpMulti + hpBonus : 0.0);
                atkList.add(atkMulti != null ? avatar.getAttackBase() * atkMulti + atkBonus : 0.0);
                defList.add(defMulti != null ? avatar.getDefenseBase() * defMulti + defBonus : 0.0);
                propList.add(propBonus);
            }
        }

        // ==========================================
        // 基础三维、元素精通与突破加成组装
        // ==========================================
        avatarBaseInfoDTO.setHp(hpList);
        avatarBaseInfoDTO.setAtk(atkList);
        avatarBaseInfoDTO.setDef(defList);
        avatarBaseInfoDTO.setAscensionStatName(translateStat(ascStatName));
        avatarBaseInfoDTO.setAscensionStatValue(propList);
        if (avatar.getElementMastery() != null) avatarBaseInfoDTO.setElementMastery(avatar.getElementMastery());

        // ==========================================
        // 材料表
        // ==========================================
        List<Integer> materialIds = new ArrayList<>();
        if (promoteLevels != null) {
            for (AvatarPromote.PromoteLevel lvl : promoteLevels) {
                if (lvl.getCostItems() == null) continue;
                for (AvatarPromote.CostItem item : lvl.getCostItems()) {
                    if (item != null && item.getId() != null) materialIds.add(item.getId());
                }
            }
        }
        Map<Integer, String> materialNameMap = new HashMap<>();
        if (!materialIds.isEmpty()) {
            materialRepository
                    .findAllById(materialIds)
                    .forEach(m -> materialNameMap.put(m.getId(), textMapChsUtil.getText(m.getNameTextMapHash())));
        }

        List<AvatarBaseInfoDTO.LevelUpMaterial> levelUpMaterials = new ArrayList<>();
        if (promoteLevels != null) {
            for (AvatarPromote.PromoteLevel lvl : promoteLevels) {
                if (lvl.getPromoteLevel() == 0) continue;
                AvatarBaseInfoDTO.LevelUpMaterial levelUpMaterial = new AvatarBaseInfoDTO.LevelUpMaterial();
                levelUpMaterial.setPromoteLevel(lvl.getPromoteLevel());
                levelUpMaterial.setMaxLevel(lvl.getUnlockMaxLevel());
                levelUpMaterial.setMora(lvl.getScoinCost());

                List<AvatarBaseInfoDTO.CostItem> costItems = new ArrayList<>();
                if (lvl.getCostItems() != null) {
                    for (AvatarPromote.CostItem item : lvl.getCostItems()) {
                        if (item == null || item.getId() == null) continue;
                        AvatarBaseInfoDTO.CostItem costItem = new AvatarBaseInfoDTO.CostItem();
                        costItem.setId(item.getId());
                        costItem.setName(materialNameMap.get(item.getId()));
                        costItem.setCount(item.getCount());
                        costItems.add(costItem);
                    }
                }
                levelUpMaterial.setCostItems(costItems);
                levelUpMaterials.add(levelUpMaterial);
            }
        }

        // 天赋材料：技能仓库取天赋组，再按组取各等级消耗
        List<AvatarBaseInfoDTO.TalentUpMaterial> talentUpMaterials = new ArrayList<>();
        AvatarSkillDepot skillDepot =
                avatarSkillDepotRepository.findById(avatar.getSkillDepotId()).orElse(null);
        Integer proudSkillGroupId = null;
        if (skillDepot != null && skillDepot.getSkills() != null) {
            for (Integer skillId : skillDepot.getSkills()) {
                // skills 末尾有 0 占位，跳过
                if (skillId == null || skillId == 0) continue;
                AvatarSkill avatarSkill =
                        avatarSkillRepository.findById(skillId).orElse(null);
                if (avatarSkill != null && avatarSkill.getProudSkillGroupId() != null) {
                    // 三个天赋共用同一套材料，只取第一个技能所在组
                    proudSkillGroupId = avatarSkill.getProudSkillGroupId();
                    break;
                }
            }
        }

        if (proudSkillGroupId != null) {
            List<ProudSkill> proudSkills =
                    proudSkillRepository.findByProudSkillGroupIdOrderByLevelAsc(proudSkillGroupId);

            List<Integer> talentMaterialIds = new ArrayList<>();
            for (ProudSkill proudSkill : proudSkills) {
                if (proudSkill.getCostItems() == null) continue;
                for (ProudSkill.CostItem item : proudSkill.getCostItems()) {
                    if (item != null && item.getId() != null) talentMaterialIds.add(item.getId());
                }
            }
            if (!talentMaterialIds.isEmpty()) {
                materialRepository
                        .findAllById(talentMaterialIds)
                        .forEach(m ->
                                materialNameMap.putIfAbsent(m.getId(), textMapChsUtil.getText(m.getNameTextMapHash())));
            }

            for (ProudSkill proudSkill : proudSkills) {
                AvatarBaseInfoDTO.TalentUpMaterial talentUpMaterial = new AvatarBaseInfoDTO.TalentUpMaterial();
                talentUpMaterial.setLevel(proudSkill.getLevel());
                talentUpMaterial.setMora(proudSkill.getCoinCost());

                List<AvatarBaseInfoDTO.CostItem> costItems = new ArrayList<>();
                if (proudSkill.getCostItems() != null) {
                    for (ProudSkill.CostItem item : proudSkill.getCostItems()) {
                        if (item == null || item.getId() == null) continue;
                        AvatarBaseInfoDTO.CostItem costItem = new AvatarBaseInfoDTO.CostItem();
                        costItem.setId(item.getId());
                        costItem.setName(materialNameMap.get(item.getId()));
                        costItem.setCount(item.getCount());
                        costItems.add(costItem);
                    }
                }
                talentUpMaterial.setCostItems(costItems);
                talentUpMaterials.add(talentUpMaterial);
            }
        }

        // ==========================================
        // 材料表组装
        // ==========================================
        AvatarBaseInfoDTO.Material material = new AvatarBaseInfoDTO.Material();
        material.setLevelUpMaterial(levelUpMaterials);
        material.setTalentUpMaterial(talentUpMaterials);
        avatarBaseInfoDTO.setMaterials(List.of(material));

        avatarBaseInfoDTO.setSplashIconName(avatar.getIconName() + "_Splash");

        return avatarBaseInfoDTO;
    }

    @Override
    public AvatarOutfitDTO getAvatarOutfits(Integer id) {
        AvatarOutfitDTO avatarOutfitDTO = new AvatarOutfitDTO();
        List<AvatarOutfitDTO.Outfit> outfits = new ArrayList<>();
        List<AvatarCostume> avatarCostumes = avatarCostumeRepository.findByCharacterId(id);

        for (AvatarCostume avatarCostume : avatarCostumes) {
            if (avatarCostume == null) continue;
            AvatarOutfitDTO.Outfit outfit = new AvatarOutfitDTO.Outfit();
            outfit.setId(avatarCostume.getId());
            outfit.setName(textMapChsUtil.getText(avatarCostume.getNameTextMapHash()));
            outfit.setDesc(textMapChsUtil.getText(avatarCostume.getDescTextMapHash()));
            outfit.setQuality(avatarCostume.getQuality());
            outfit.setIndexID(avatarCostume.getIndexID());
            outfits.add(outfit);
        }

        outfits.sort((a, b) -> {
            int i1 = a.getIndexID() == null ? -1 : a.getIndexID();
            int i2 = b.getIndexID() == null ? -1 : b.getIndexID();
            return Integer.compare(i1, i2);
        });

        avatarOutfitDTO.setId(id);
        avatarOutfitDTO.setOutfits(outfits);

        return avatarOutfitDTO;
    }

    @Override
    public AvatarStoryDTO getAvatarStories(Integer id) {
        AvatarStoryDTO avatarStoryDTO = new AvatarStoryDTO();
        List<AvatarStoryDTO.Story> stories = new ArrayList<>();
        List<FetterStory> fetterStories = fetterStoryRepository.findByAvatarIdOrderByFetterIdAsc(id);

        if (fetterStories != null) {
            for (FetterStory fetterStory : fetterStories) {
                if (fetterStory == null) continue;

                AvatarStoryDTO.Story story = new AvatarStoryDTO.Story();
                story.setTitle(textMapChsUtil.getText(fetterStory.getStoryTitleTextMapHash()));
                story.setText(textMapChsUtil.getText(fetterStory.getStoryContextTextMapHash()));

                List<String> tips = new ArrayList<>();
                if (fetterStory.getTips() != null) {
                    for (String tipHash : fetterStory.getTips()) {
                        String tip = textMapChsUtil.getText(tipHash);
                        if (tip != null) tips.add(tip);
                    }
                }
                story.setTips(tips);

                stories.add(story);
            }
        }

        avatarStoryDTO.setId(id);
        avatarStoryDTO.setStories(stories);
        return avatarStoryDTO;
    }

    @Override
    public AvatarVoiceDTO getAvatarVoices(Integer id) {
        AvatarVoiceDTO avatarVoiceDTO = new AvatarVoiceDTO();
        List<AvatarVoiceDTO.Daily> dailies = new ArrayList<>();
        List<AvatarVoiceDTO.Battle> battles = new ArrayList<>();

        List<Fetters> fettersList = fettersRepository.findByAvatarIdOrderByTypeAscFetterIdAsc(id);

        if (fettersList != null) {
            for (Fetters fetters : fettersList) {
                if (fetters == null) continue;

                List<String> tips = new ArrayList<>();
                if (fetters.getTips() != null) {
                    for (String tipHash : fetters.getTips()) {
                        String tip = textMapChsUtil.getText(tipHash);
                        if (tip != null) tips.add(tip);
                    }
                }

                if (fetters.getType() != null && fetters.getType() == 1) {
                    AvatarVoiceDTO.Daily daily = new AvatarVoiceDTO.Daily();
                    daily.setTitle(textMapChsUtil.getText(fetters.getVoiceTitleTextMapHash()));
                    daily.setText(textMapChsUtil.getText(fetters.getVoiceFileTextTextMapHash()));
                    daily.setTips(tips);
                    dailies.add(daily);

                } else if (fetters.getType() != null && fetters.getType() == 2) {
                    AvatarVoiceDTO.Battle battle = new AvatarVoiceDTO.Battle();
                    battle.setTitle(textMapChsUtil.getText(fetters.getVoiceTitleTextMapHash()));
                    battle.setText(textMapChsUtil.getText(fetters.getVoiceFileTextTextMapHash()));
                    battle.setTips(tips);
                    battles.add(battle);
                }
            }
        }

        avatarVoiceDTO.setId(id);
        avatarVoiceDTO.setDailies(dailies);
        avatarVoiceDTO.setBattles(battles);

        return avatarVoiceDTO;
    }

    @Override
    public AvatarConstellationDTO getAvatarConstellations(Integer id) {
        AvatarConstellationDTO avatarConstellationDTO = new AvatarConstellationDTO();
        List<AvatarConstellationDTO.Constellation> constellations = new ArrayList<>();

        Avatar avatar = avatarRepository.findById(id).orElse(null);
        if (avatar == null) return null;

        // 命座顺序由技能仓库的 talents 数组决定，下标 0–5 即 1–6 命
        AvatarSkillDepot avatarSkillDepot =
                avatarSkillDepotRepository.findById(avatar.getSkillDepotId()).orElse(null);
        if (avatarSkillDepot != null && avatarSkillDepot.getTalents() != null) {
            List<Integer> talentIds = new ArrayList<>();
            for (Integer talentId : avatarSkillDepot.getTalents()) {
                // talents 末尾有 0 占位，跳过
                if (talentId == null || talentId == 0) continue;
                talentIds.add(talentId);
            }

            Map<Integer, AvatarTalent> talentMap = new HashMap<>();
            if (!talentIds.isEmpty()) {
                avatarTalentRepository.findByIdIn(talentIds).forEach(t -> talentMap.put(t.getId(), t));
            }

            for (int i = 0; i < talentIds.size(); i++) {
                Integer talentId = talentIds.get(i);
                AvatarTalent avatarTalent = talentMap.get(talentId);
                if (avatarTalent == null) continue;

                AvatarConstellationDTO.Constellation constellation = new AvatarConstellationDTO.Constellation();
                constellation.setId(avatarTalent.getId());
                constellation.setName(textMapChsUtil.getText(avatarTalent.getNameTextMapHash()));
                constellation.setDesc(textMapChsUtil.getText(avatarTalent.getDescTextMapHash()));
                constellation.setDescExtra(textMapChsUtil.getText(avatarTalent.getDescExtraTextMapHash()));

                // 后缀数字为 i + 1 (即 1~6)
                constellation.setConstellationIconName(avatar.getIconName() + "_Constellation_" + (i + 1));

                constellations.add(constellation);
            }
        }

        avatarConstellationDTO.setId(id);
        avatarConstellationDTO.setConstellations(constellations);

        return avatarConstellationDTO;
    }

    @Override
    public AvatarSkillDTO getAvatarSkills(Integer id) {
        AvatarSkillDTO avatarSkillDTO = new AvatarSkillDTO();
        List<AvatarSkillDTO.CombatSkill> combatSkills = new ArrayList<>();
        List<AvatarSkillDTO.PassiveSkill> passiveSkills = new ArrayList<>();
        List<AvatarSkillDTO.SpecialProudSkill> specialProudSkills = new ArrayList<>();
        int passiveIndex = 1;

        Avatar avatar = avatarRepository.findById(id).orElse(null);
        if (avatar == null) return null;

        AvatarSkillDepot avatarSkillDepot =
                avatarSkillDepotRepository.findById(avatar.getSkillDepotId()).orElse(null);
        if (avatarSkillDepot != null) {

            Map<Integer, String> skillIconSuffixMap = new HashMap<>();
            if (avatarSkillDepot.getSkills() != null) {
                List<Integer> depotSkills = avatarSkillDepot.getSkills();
                // 索引 0 为普通攻击
                if (depotSkills.size() > 0 && depotSkills.get(0) != null && depotSkills.get(0) != 0) {
                    skillIconSuffixMap.put(depotSkills.get(0), "NormalAttack");
                }
                // 索引 1 为元素战技
                if (depotSkills.size() > 1 && depotSkills.get(1) != null && depotSkills.get(1) != 0) {
                    skillIconSuffixMap.put(depotSkills.get(1), "ElementSkill");
                }
            }
            // energySkill 为元素爆发
            Integer energySkillId = avatarSkillDepot.getEnergySkill();
            if (energySkillId != null && energySkillId != 0) {
                skillIconSuffixMap.put(energySkillId, "ElementBurst");
            }

            // 主动技能：skills 里是普攻、战技及霰步这类附属技能，元素爆发单独在 energySkill 里
            List<Integer> skillIds = new ArrayList<>();
            if (avatarSkillDepot.getSkills() != null) {
                for (Integer skillId : avatarSkillDepot.getSkills()) {
                    // skills 末尾有 0 占位，跳过
                    if (skillId == null || skillId == 0) continue;
                    // 跳过 ID 大于五位数的技能
                    if (skillId >= 100000) continue;
                    // 跳过部分特殊技能
                    if (IGNORE_SKILLS.contains(skillId)) continue;

                    skillIds.add(skillId);
                }
            }
            if (energySkillId != null && energySkillId != 0 && !skillIds.contains(energySkillId)) {
                skillIds.add(energySkillId);
            }

            for (Integer skillId : skillIds) {
                AvatarSkill avatarSkill =
                        avatarSkillRepository.findById(skillId).orElse(null);
                if (avatarSkill == null) continue;

                AvatarSkillDTO.CombatSkill combatSkill = new AvatarSkillDTO.CombatSkill();
                combatSkill.setId(avatarSkill.getId());
                combatSkill.setName(textMapChsUtil.getText(avatarSkill.getNameTextMapHash()));
                combatSkill.setDesc(textMapChsUtil.getText(avatarSkill.getDescTextMapHash()));
                combatSkill.setExtraDesc(textMapChsUtil.getText(avatarSkill.getExtraDescTextMapHash()));
                combatSkill.setDescExtra(textMapChsUtil.getText(avatarSkill.getDescExtraTextMapHash()));

                // === 修改：判断当前技能类型并拼接对应图标 ===
                String suffix = skillIconSuffixMap.get(skillId);
                if (suffix != null) {
                    combatSkill.setSkillIconName(avatar.getIconName() + "_Talent_" + suffix);
                } else {
                    // 兜底：如果是霰步等没在 map 里的特殊技能，保留原本的 skillIcon
                    combatSkill.setSkillIconName(avatarSkill.getSkillIcon());
                }

                // 技能数值按天赋组逐级展开，组内等级 1–15 都保留
                List<AvatarSkillDTO.CombatSkillData> combatSkillDataList = new ArrayList<>();
                if (avatarSkill.getProudSkillGroupId() != null) {
                    List<ProudSkill> proudSkills = proudSkillRepository.findByProudSkillGroupIdOrderByLevelAsc(
                            avatarSkill.getProudSkillGroupId());
                    for (ProudSkill proudSkill : proudSkills) {
                        combatSkillDataList.add(buildCombatSkillData(proudSkill));
                    }
                }
                combatSkill.setCombatSkillDataList(combatSkillDataList);

                combatSkills.add(combatSkill);
            }

            // 固有天赋：每个天赋组只取 1 级那条，组里其余等级是升级节点
            if (avatarSkillDepot.getProudSkillGroups() != null) {
                for (AvatarSkillDepot.ProudSkillGroup proudSkillGroup : avatarSkillDepot.getProudSkillGroups()) {
                    if (proudSkillGroup == null || proudSkillGroup.getProudSkillGroupId() == null) continue;

                    List<ProudSkill> proudSkills = proudSkillRepository.findByProudSkillGroupIdOrderByLevelAsc(
                            proudSkillGroup.getProudSkillGroupId());
                    for (ProudSkill proudSkill : proudSkills) {
                        if (proudSkill.getLevel() == null || proudSkill.getLevel() != 1) continue;

                        AvatarSkillDTO.PassiveSkill passiveSkill = new AvatarSkillDTO.PassiveSkill();
                        passiveSkill.setId(proudSkill.getId());
                        passiveSkill.setName(textMapChsUtil.getText(proudSkill.getNameTextMapHash()));
                        passiveSkill.setDesc(textMapChsUtil.getText(proudSkill.getDescTextMapHash()));
                        passiveSkill.setDescExtra(textMapChsUtil.getText(proudSkill.getDescExtraTextMapHash()));
                        passiveSkill.setLoreDesc(textMapChsUtil.getText(proudSkill.getLoreDescTextMapHash()));

                        passiveSkill.setSkillIconName(avatar.getIconName() + "_Passive_" + passiveIndex);
                        passiveIndex++;

                        passiveSkills.add(passiveSkill);
                        break;
                    }
                }
            }

            if (avatarSkillDepot.getSpecialProudSkillGroups() != null) {
                for (AvatarSkillDepot.SpecialProudSkillGroup specialProudSkillGroup :
                        avatarSkillDepot.getSpecialProudSkillGroups()) {
                    if (specialProudSkillGroup == null || specialProudSkillGroup.getProudSkillGroupId() == null)
                        continue;

                    List<ProudSkill> proudSkills = proudSkillRepository.findByProudSkillGroupIdOrderByLevelAsc(
                            specialProudSkillGroup.getProudSkillGroupId());
                    for (ProudSkill proudSkill : proudSkills) {
                        if (proudSkill.getLevel() == null || proudSkill.getLevel() != 1) continue;

                        AvatarSkillDTO.SpecialProudSkill specialProudSkill = new AvatarSkillDTO.SpecialProudSkill();
                        specialProudSkill.setId(proudSkill.getId());
                        specialProudSkill.setName(textMapChsUtil.getText(proudSkill.getNameTextMapHash()));
                        specialProudSkill.setDesc(textMapChsUtil.getText(proudSkill.getDescTextMapHash()));
                        specialProudSkill.setDescExtra(textMapChsUtil.getText(proudSkill.getDescExtraTextMapHash()));
                        specialProudSkill.setLoreDesc(textMapChsUtil.getText(proudSkill.getLoreDescTextMapHash()));

                        specialProudSkill.setSkillIconName(avatar.getIconName() + "_Passive_" + passiveIndex);
                        passiveIndex++;

                        specialProudSkills.add(specialProudSkill);
                        break;
                    }
                }
            }
        }

        avatarSkillDTO.setId(id);
        avatarSkillDTO.setCombatSkills(combatSkills);
        avatarSkillDTO.setPassiveSkills(passiveSkills);
        avatarSkillDTO.setSpecialProudSkills(specialProudSkills);

        return avatarSkillDTO;
    }

    private AvatarSkillDTO.CombatSkillData buildCombatSkillData(ProudSkill proudSkill) {
        AvatarSkillDTO.CombatSkillData combatSkillData = new AvatarSkillDTO.CombatSkillData();
        combatSkillData.setLevel(proudSkill.getLevel());
        combatSkillData.setParamList(proudSkill.getParamList());

        // paramDescList 存的是哈希，逐条翻译；解不出的直接跳过
        List<String> paramDescList = new ArrayList<>();
        if (proudSkill.getParamDescList() != null) {
            for (String paramDescHash : proudSkill.getParamDescList()) {
                String paramDesc = textMapChsUtil.getText(paramDescHash);
                if (paramDesc != null) paramDescList.add(paramDesc);
            }
        }
        combatSkillData.setParamDescList(paramDescList);

        return combatSkillData;
    }

    private String translateQuality(String quality) {
        if (quality == null) return "未知";
        return switch (quality) {
            case "QUALITY_ORANGE" -> "五星";
            case "QUALITY_ORANGE_SP" -> "五星";
            case "QUALITY_PURPLE" -> "四星";
            case "QUALITY_BLUE" -> "三星";
            default -> quality;
        };
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

    private String translateElement(String element) {
        if (element == null) return "UNKNOWN";
        return switch (element) {
            case "火" -> "PYRO";
            case "水" -> "HYDRO";
            case "冰" -> "CRYO";
            case "雷" -> "ELECTRO";
            case "风" -> "ANEMO";
            case "岩" -> "GEO";
            case "草" -> "DENDRO";
            default -> element;
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
