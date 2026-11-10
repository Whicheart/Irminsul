package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.*;
import com.genshin.irminsul.entity.*;
import com.genshin.irminsul.repository.*;
import com.genshin.irminsul.service.AvatarService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    private static final String ASSOC_PREFIX = "ASSOC_TYPE_";
    private static final int MAX_LEVEL = 100;
    private static final int MAX_PROMOTE_LEVEL = 6;

    private final AvatarRepository avatarRepository;
    private final AvatarCodexRepository avatarCodexRepository;
    private final FettersRepository fettersRepository;
    private final FetterInfoRepository fetterInfoRepository;
    private final FetterStoryRepository fetterStoryRepository;
    private final AvatarCurveRepository avatarCurveRepository;
    private final AvatarPromoteRepository avatarPromoteRepository;
    private final MaterialRepository materialRepository;
    private final AvatarCostumeRepository avatarCostumeRepository;
    private final AvatarSkillDepotRepository avatarSkillDepotRepository;
    private final AvatarSkillRepository avatarSkillRepository;
    private final AvatarTalentRepository avatarTalentRepository;
    private final ProudSkillRepository proudSkillRepository;
    private final TextMapChsUtil textMapChsUtil;

    private static final Set<String> EXCLUDED_AVATAR_NAMES = Set.of("旅行者", "奇偶·男性", "奇偶·女性");
    private static final Set<Integer> EXCLUDED_SKILL_IDS = Set.of(30506, 11076);
    private static final Set<Integer> EXCLUDED_PASSIVE_SKILL_IDS = Set.of(522301);
    // 神明清单，用神之心
    private static final Set<Integer> HEART_OF_GOD_AVATAR_IDS =
            Set.of(10000022, 10000030, 10000052, 10000073, 10000106);

    // 曲线表与等级无关，进程内缓存，改库后重启生效
    private volatile Map<Integer, AvatarCurve> avatarCurveCache;

    @Override
    public List<AvatarList.AvatarBrief> getAvatarList(
            String name, List<String> elements, List<String> weaponTypes, List<String> qualityTypes) {
        List<AvatarRepository.AvatarBriefView> candidates = avatarRepository.findAllBrief().stream()
                .filter(avatar -> avatar.getId() != null && avatar.getId() >= 10000002 && avatar.getId() <= 10000899)
                .toList();

        Map<Integer, AvatarCodexRepository.AvatarCodexBriefView> codexById =
                avatarCodexRepository.findAllBrief().stream()
                        .filter(codex -> codex.getId() != null)
                        .collect(Collectors.toMap(
                                AvatarCodexRepository.AvatarCodexBriefView::getId,
                                codex -> codex,
                                (existing, replacement) -> existing));
        Map<Integer, FetterInfoRepository.FetterInfoBriefView> infoById = fetterInfoRepository.findAllBrief().stream()
                .filter(info -> info.getId() != null)
                .collect(Collectors.toMap(
                        FetterInfoRepository.FetterInfoBriefView::getId,
                        info -> info,
                        (existing, replacement) -> existing));

        Set<String> hashes = new HashSet<>();
        for (AvatarRepository.AvatarBriefView avatar : candidates) {
            hashes.add(avatar.getNameTextMapHash());
            FetterInfoRepository.FetterInfoBriefView info = infoById.get(avatar.getId());
            if (info != null) {
                hashes.add(info.getAvatarVisionBeforTextMapHash());
            }
        }
        Map<String, String> texts = textMapChsUtil.getTexts(hashes);

        String nameFilter = name == null || name.trim().isEmpty() ? null : name.trim();
        List<AvatarList.AvatarBrief> result = new ArrayList<>();
        for (AvatarRepository.AvatarBriefView avatar : candidates) {
            String avatarName = texts.get(avatar.getNameTextMapHash());
            if (avatarName == null || EXCLUDED_AVATAR_NAMES.contains(avatarName)) {
                continue;
            }
            FetterInfoRepository.FetterInfoBriefView info = infoById.get(avatar.getId());
            String element = info == null ? null : translateElement(texts.get(info.getAvatarVisionBeforTextMapHash()));

            if (nameFilter != null && !avatarName.contains(nameFilter)) {
                continue;
            }
            if (elements != null && !elements.isEmpty() && !elements.contains(element)) {
                continue;
            }
            if (weaponTypes != null && !weaponTypes.isEmpty() && !weaponTypes.contains(avatar.getWeaponType())) {
                continue;
            }
            if (qualityTypes != null && !qualityTypes.isEmpty() && !qualityTypes.contains(avatar.getQualityType())) {
                continue;
            }

            AvatarCodexRepository.AvatarCodexBriefView codex = codexById.get(avatar.getId());
            AvatarList.AvatarBrief brief = new AvatarList.AvatarBrief();
            brief.setId(avatar.getId());
            brief.setSortId(codex == null ? null : codex.getSortId());
            brief.setName(avatarName);
            brief.setQualityType(avatar.getQualityType());
            brief.setElement(element);
            brief.setWeaponType(avatar.getWeaponType());
            brief.setGachaIconName(toGachaAvatarIcon(avatar.getIconName()));
            brief.setIconName(avatar.getIconName());
            result.add(brief);
        }

        result.sort(Comparator.comparing(
                (AvatarList.AvatarBrief brief) -> brief.getSortId() == null ? -1 : brief.getSortId(),
                Comparator.reverseOrder()));
        return result;
    }

    @Override
    public AvatarBaseInfoDTO getAvatarBaseInfo(Integer id) {
        Avatar avatar = avatarRepository.findById(id).orElse(null);
        if (avatar == null) {
            return null;
        }

        AvatarCodex codex = avatarCodexRepository.findById(id).orElse(new AvatarCodex());
        AvatarPromote promote = avatar.getAvatarPromoteId() == null
                ? new AvatarPromote()
                : avatarPromoteRepository.findById(avatar.getAvatarPromoteId()).orElse(new AvatarPromote());
        FetterInfo fetter = fetterInfoRepository.findById(id).orElse(new FetterInfo());
        AvatarSkillDepot depot = avatar.getSkillDepotId() == null
                ? null
                : avatarSkillDepotRepository.findById(avatar.getSkillDepotId()).orElse(null);

        Map<String, String> texts = textMapChsUtil.getTexts(List.of(
                avatar.getNameTextMapHash(),
                avatar.getInfoDescTextMapHash(),
                avatar.getDescTextMapHash(),
                fetter.getAvatarTitleTextMapHash(),
                fetter.getAvatarNativeTextMapHash(),
                fetter.getAvatarNative2TextMapHash(),
                fetter.getAvatarConstellationBeforTextMapHash(),
                fetter.getAvatarConstellationAfterTextMapHash(),
                fetter.getAvatarPowerBeforTextMapHash(),
                fetter.getAvatarPowerAfterTextMapHash(),
                fetter.getAvatarVisionBeforTextMapHash(),
                fetter.getAvatarVisionAfterTextMapHash(),
                fetter.getCvChineseTextMapHash(),
                fetter.getCvEnglishTextMapHash(),
                fetter.getCvJapaneseTextMapHash(),
                fetter.getCvKoreanTextMapHash()));

        AvatarBaseInfoDTO dto = new AvatarBaseInfoDTO();
        dto.setId(avatar.getId());
        dto.setTitle(texts.get(fetter.getAvatarTitleTextMapHash()));
        dto.setName(texts.get(avatar.getNameTextMapHash()));
        String infoDesc = texts.get(avatar.getInfoDescTextMapHash());
        dto.setDesc(infoDesc != null ? infoDesc : texts.get(avatar.getDescTextMapHash()));
        dto.setQualityType(avatar.getQualityType());
        dto.setWeaponType(avatar.getWeaponType());
        dto.setReleaseTime(codex.getBeginTime());
        dto.setCountry(fetter.getAvatarAssocType());
        dto.setTags(avatar.getTags());
        dto.setArkheType(depot == null ? null : depot.getArkheType());

        List<String> affiliation = new ArrayList<>();
        String native1 = texts.get(fetter.getAvatarNativeTextMapHash());
        String native2 = texts.get(fetter.getAvatarNative2TextMapHash());
        if (native1 != null && !native1.isEmpty()) {
            affiliation.add(native1);
        }
        if (native2 != null && !native2.isEmpty()) {
            affiliation.add(native2);
        }
        dto.setAffiliation(affiliation);

        AvatarBaseInfoDTO.Birth birth = new AvatarBaseInfoDTO.Birth();
        birth.setMonth(fetter.getInfoBirthMonth());
        birth.setDay(fetter.getInfoBirthDay());
        dto.setBirth(birth);

        AvatarBaseInfoDTO.Constellation constellation = new AvatarBaseInfoDTO.Constellation();
        constellation.setBefor(texts.get(fetter.getAvatarConstellationBeforTextMapHash()));
        constellation.setAfter(texts.get(fetter.getAvatarConstellationAfterTextMapHash()));
        dto.setConstellation(constellation);

        String defaultPower = HEART_OF_GOD_AVATAR_IDS.contains(avatar.getId()) ? "神之心" : "神之眼";
        AvatarBaseInfoDTO.Vision vision = new AvatarBaseInfoDTO.Vision();
        vision.setBefor(orDefault(texts.get(fetter.getAvatarPowerBeforTextMapHash()), defaultPower));
        vision.setAfter(texts.get(fetter.getAvatarPowerAfterTextMapHash()));
        vision.setIconName(toVisionIconName(fetter.getAvatarAssocType(), depot == null ? null : depot.getArkheType()));
        dto.setVision(vision);

        String elementText = texts.get(fetter.getAvatarVisionBeforTextMapHash());
        AvatarBaseInfoDTO.Element element = new AvatarBaseInfoDTO.Element();
        element.setBefor(elementText);
        element.setAfter(texts.get(fetter.getAvatarVisionAfterTextMapHash()));
        element.setIconName(toElementIconName(fetter.getAvatarAssocType(), translateElement(elementText)));
        dto.setElement(element);

        AvatarBaseInfoDTO.Cv cv = new AvatarBaseInfoDTO.Cv();
        cv.setCn(texts.get(fetter.getCvChineseTextMapHash()));
        cv.setEn(texts.get(fetter.getCvEnglishTextMapHash()));
        cv.setJp(texts.get(fetter.getCvJapaneseTextMapHash()));
        cv.setKr(texts.get(fetter.getCvKoreanTextMapHash()));
        dto.setCv(cv);

        buildAttributes(avatar, promote, dto);

        dto.setMaterials(List.of(buildMaterials(promote, depot)));
        dto.setGachaImageName(toGachaAvatarImage(avatar.getIconName()));
        return dto;
    }

    private void buildAttributes(Avatar avatar, AvatarPromote promote, AvatarBaseInfoDTO dto) {
        Map<Integer, AvatarCurve> curves = avatarCurves();
        List<Avatar.PropGrowCurve> growCurves = avatar.getPropGrowCurves();

        String hpCurve = null;
        String atkCurve = null;
        String defCurve = null;
        if (growCurves != null) {
            for (Avatar.PropGrowCurve growCurve : growCurves) {
                if ("FIGHT_PROP_BASE_HP".equals(growCurve.getType())) {
                    hpCurve = growCurve.getGrowCurve();
                } else if ("FIGHT_PROP_BASE_ATTACK".equals(growCurve.getType())) {
                    atkCurve = growCurve.getGrowCurve();
                } else if ("FIGHT_PROP_BASE_DEFENSE".equals(growCurve.getType())) {
                    defCurve = growCurve.getGrowCurve();
                }
            }
        }

        double[] hpBonus = new double[MAX_PROMOTE_LEVEL + 1];
        double[] atkBonus = new double[MAX_PROMOTE_LEVEL + 1];
        double[] defBonus = new double[MAX_PROMOTE_LEVEL + 1];
        double[] ascValue = new double[MAX_PROMOTE_LEVEL + 1];
        String ascType = null;

        List<AvatarPromote.PromoteLevel> promoteLevels = promote.getLevels();
        if (promoteLevels != null) {
            for (AvatarPromote.PromoteLevel promoteLevel : promoteLevels) {
                Integer level = promoteLevel.getPromoteLevel();
                if (level == null || level < 0 || level > MAX_PROMOTE_LEVEL) {
                    continue;
                }
                if (promoteLevel.getAddProps() == null) {
                    continue;
                }
                for (AvatarPromote.AddProp prop : promoteLevel.getAddProps()) {
                    if (prop.getPropType() == null || prop.getPropType().isEmpty()) {
                        continue;
                    }
                    double value = prop.getValue() == null ? 0.0 : prop.getValue();
                    switch (prop.getPropType()) {
                        case "FIGHT_PROP_BASE_HP" -> hpBonus[level] = value;
                        case "FIGHT_PROP_BASE_ATTACK" -> atkBonus[level] = value;
                        case "FIGHT_PROP_BASE_DEFENSE" -> defBonus[level] = value;
                        default -> {
                            if (ascType == null) {
                                ascType = prop.getPropType();
                            }
                            if (ascType.equals(prop.getPropType())) {
                                ascValue[level] = value;
                            }
                        }
                    }
                }
            }
        }

        double hpBase = avatar.getHpBase() == null ? 0.0 : avatar.getHpBase();
        double atkBase = avatar.getAttackBase() == null ? 0.0 : avatar.getAttackBase();
        double defBase = avatar.getDefenseBase() == null ? 0.0 : avatar.getDefenseBase();

        List<Double> hp = new ArrayList<>(MAX_LEVEL);
        List<Double> atk = new ArrayList<>(MAX_LEVEL);
        List<Double> def = new ArrayList<>(MAX_LEVEL);
        List<Double> ascension = new ArrayList<>(MAX_LEVEL);
        for (int level = 1; level <= MAX_LEVEL; level++) {
            AvatarCurve curve = curves.get(level);
            int promoteLevel = toPromoteLevel(level);
            hp.add(hpBase * curveValue(curve, hpCurve) + hpBonus[promoteLevel]);
            atk.add(atkBase * curveValue(curve, atkCurve) + atkBonus[promoteLevel]);
            def.add(defBase * curveValue(curve, defCurve) + defBonus[promoteLevel]);
            ascension.add(ascValue[promoteLevel]);
        }

        dto.setHp(hp);
        dto.setAtk(atk);
        dto.setDef(def);
        dto.setElementMastery(avatar.getElementMastery());
        dto.setAscensionStatName(translateStat(ascType));
        dto.setAscensionStatValue(ascension);
    }

    private AvatarBaseInfoDTO.Material buildMaterials(AvatarPromote promote, AvatarSkillDepot depot) {
        List<AvatarBaseInfoDTO.LevelUpMaterial> levelUpMaterials = new ArrayList<>();
        Set<Integer> materialIds = new LinkedHashSet<>();
        List<AvatarPromote.PromoteLevel> promoteLevels = promote.getLevels();
        if (promoteLevels != null) {
            for (AvatarPromote.PromoteLevel promoteLevel : promoteLevels) {
                if (promoteLevel.getCostItems() == null) {
                    continue;
                }
                for (AvatarPromote.CostItem item : promoteLevel.getCostItems()) {
                    if (item != null && item.getId() != null) {
                        materialIds.add(item.getId());
                    }
                }
            }
        }

        List<ProudSkill> proudSkills = new ArrayList<>();
        Integer proudSkillGroupId = findProudSkillGroupId(depot);
        if (proudSkillGroupId != null) {
            proudSkills = proudSkillRepository.findByProudSkillGroupIdInOrderByProudSkillGroupIdAscLevelAsc(
                    List.of(proudSkillGroupId));
            for (ProudSkill proudSkill : proudSkills) {
                if (proudSkill.getCostItems() == null) {
                    continue;
                }
                for (ProudSkill.CostItem item : proudSkill.getCostItems()) {
                    if (item != null && item.getId() != null) {
                        materialIds.add(item.getId());
                    }
                }
            }
        }

        Map<Integer, MaterialRepository.MaterialBriefView> materials =
                materialRepository.findBriefByIdIn(materialIds).stream()
                        .filter(material -> material.getId() != null)
                        .collect(Collectors.toMap(
                                MaterialRepository.MaterialBriefView::getId,
                                material -> material,
                                (existing, replacement) -> existing));
        Map<String, String> materialTexts = textMapChsUtil.getTexts(materials.values().stream()
                .map(MaterialRepository.MaterialBriefView::getNameTextMapHash)
                .toList());
        Map<Integer, String> materialNames = new HashMap<>();
        materials.forEach((materialId, material) ->
                materialNames.put(materialId, materialTexts.get(material.getNameTextMapHash())));

        if (promoteLevels != null) {
            for (AvatarPromote.PromoteLevel promoteLevel : promoteLevels) {
                Integer level = promoteLevel.getPromoteLevel();
                if (level == null || level < 1) {
                    continue;
                }
                AvatarBaseInfoDTO.LevelUpMaterial levelUpMaterial = new AvatarBaseInfoDTO.LevelUpMaterial();
                levelUpMaterial.setPromoteLevel(level);
                levelUpMaterial.setUnlockMaxLevel(promoteLevel.getUnlockMaxLevel());
                levelUpMaterial.setMoraCost(promoteLevel.getScoinCost());
                levelUpMaterial.setCostItems(
                        buildAvatarCostItems(promoteLevel.getCostItems(), materials, materialNames));
                levelUpMaterials.add(levelUpMaterial);
            }
        }

        List<AvatarBaseInfoDTO.TalentUpMaterial> talentUpMaterials = new ArrayList<>();
        for (ProudSkill proudSkill : proudSkills) {
            AvatarBaseInfoDTO.TalentUpMaterial talentUpMaterial = new AvatarBaseInfoDTO.TalentUpMaterial();
            talentUpMaterial.setLevel(proudSkill.getLevel());
            talentUpMaterial.setMoraCost(proudSkill.getCoinCost());
            talentUpMaterial.setCostItems(buildProudCostItems(proudSkill.getCostItems(), materials, materialNames));
            talentUpMaterials.add(talentUpMaterial);
        }

        AvatarBaseInfoDTO.Material material = new AvatarBaseInfoDTO.Material();
        material.setLevelUpMaterial(levelUpMaterials);
        material.setTalentUpMaterial(talentUpMaterials);
        return material;
    }

    private List<AvatarBaseInfoDTO.CostItem> buildAvatarCostItems(
            List<AvatarPromote.CostItem> costItems,
            Map<Integer, MaterialRepository.MaterialBriefView> materials,
            Map<Integer, String> materialNames) {
        List<AvatarBaseInfoDTO.CostItem> result = new ArrayList<>();
        if (costItems == null) {
            return result;
        }
        for (AvatarPromote.CostItem item : costItems) {
            if (item == null || item.getId() == null) {
                continue;
            }
            AvatarBaseInfoDTO.CostItem costItem = new AvatarBaseInfoDTO.CostItem();
            costItem.setId(item.getId());
            costItem.setName(materialNames.get(item.getId()));
            costItem.setCount(item.getCount());
            MaterialRepository.MaterialBriefView material = materials.get(item.getId());
            costItem.setIconName(material == null ? null : material.getIcon());
            result.add(costItem);
        }
        return result;
    }

    private List<AvatarBaseInfoDTO.CostItem> buildProudCostItems(
            List<ProudSkill.CostItem> costItems,
            Map<Integer, MaterialRepository.MaterialBriefView> materials,
            Map<Integer, String> materialNames) {
        List<AvatarBaseInfoDTO.CostItem> result = new ArrayList<>();
        if (costItems == null) {
            return result;
        }
        for (ProudSkill.CostItem item : costItems) {
            if (item == null || item.getId() == null) {
                continue;
            }
            AvatarBaseInfoDTO.CostItem costItem = new AvatarBaseInfoDTO.CostItem();
            costItem.setId(item.getId());
            costItem.setName(materialNames.get(item.getId()));
            costItem.setCount(item.getCount());
            MaterialRepository.MaterialBriefView material = materials.get(item.getId());
            costItem.setIconName(material == null ? null : material.getIcon());
            result.add(costItem);
        }
        return result;
    }

    private Integer findProudSkillGroupId(AvatarSkillDepot depot) {
        if (depot == null || depot.getSkills() == null) {
            return null;
        }
        List<Integer> skillIds = depot.getSkills().stream()
                .filter(skillId -> skillId != null && skillId != 0)
                .toList();
        if (skillIds.isEmpty()) {
            return null;
        }
        Map<Integer, AvatarSkillRepository.AvatarSkillBriefView> skills =
                avatarSkillRepository.findBriefByIdIn(skillIds).stream()
                        .filter(skill -> skill.getId() != null)
                        .collect(Collectors.toMap(
                                AvatarSkillRepository.AvatarSkillBriefView::getId,
                                skill -> skill,
                                (existing, replacement) -> existing));
        for (Integer skillId : skillIds) {
            AvatarSkillRepository.AvatarSkillBriefView skill = skills.get(skillId);
            if (skill != null && skill.getProudSkillGroupId() != null) {
                return skill.getProudSkillGroupId();
            }
        }
        return null;
    }

    @Override
    public AvatarOutfitDTO getAvatarOutfits(Integer id) {
        Avatar avatar = avatarRepository.findById(id).orElse(null);
        List<AvatarCostume> costumes = avatarCostumeRepository.findByCharacterId(id);
        Map<String, String> texts = textMapChsUtil.getTexts(costumes.stream()
                .flatMap(costume ->
                        java.util.stream.Stream.of(costume.getNameTextMapHash(), costume.getDescTextMapHash()))
                .toList());

        List<AvatarOutfitDTO.Outfit> outfits = new ArrayList<>();
        for (AvatarCostume costume : costumes) {
            AvatarOutfitDTO.Outfit outfit = new AvatarOutfitDTO.Outfit();
            outfit.setId(costume.getId());
            outfit.setName(texts.get(costume.getNameTextMapHash()));
            outfit.setDesc(texts.get(costume.getDescTextMapHash()));
            outfit.setQuality(costume.getQuality());
            outfit.setIndexID(costume.getIndexID());
            outfit.setIsDefault(Boolean.TRUE.equals(costume.getIsDefault()));
            // 默认时装用角色抽卡立绘
            outfit.setImageName(
                    Boolean.TRUE.equals(costume.getIsDefault())
                            ? toGachaAvatarImage(avatar == null ? null : avatar.getIconName())
                            : toCostumeImage(costume.getFrontIconName()));
            outfit.setAvatarCardName(
                    avatar == null || avatar.getIconName() == null ? null : avatar.getIconName() + "_Card");
            outfit.setCostumeCardName(costume.getFrontIconName() == null ? null : costume.getFrontIconName() + "_Card");
            outfits.add(outfit);
        }

        outfits.sort(Comparator.comparing(
                (AvatarOutfitDTO.Outfit outfit) -> outfit.getIndexID() == null ? -1 : outfit.getIndexID()));
        AvatarOutfitDTO dto = new AvatarOutfitDTO();
        dto.setId(id);
        dto.setOutfits(outfits);
        return dto;
    }

    @Override
    public AvatarStoryDTO getAvatarStories(Integer id) {
        List<FetterStory> stories = fetterStoryRepository.findByAvatarIdOrderByFetterIdAsc(id);
        Set<String> hashes = new LinkedHashSet<>();
        for (FetterStory story : stories) {
            hashes.add(story.getStoryTitleTextMapHash());
            hashes.add(story.getStoryContextTextMapHash());
            if (story.getTips() != null) {
                hashes.addAll(story.getTips());
            }
        }
        Map<String, String> texts = textMapChsUtil.getTexts(hashes);

        List<AvatarStoryDTO.Story> result = new ArrayList<>();
        for (FetterStory story : stories) {
            AvatarStoryDTO.Story item = new AvatarStoryDTO.Story();
            item.setTitle(texts.get(story.getStoryTitleTextMapHash()));
            item.setText(texts.get(story.getStoryContextTextMapHash()));
            item.setTips(translateTips(story.getTips(), texts));
            result.add(item);
        }

        AvatarStoryDTO dto = new AvatarStoryDTO();
        dto.setId(id);
        dto.setStories(result);
        return dto;
    }

    @Override
    public AvatarVoiceDTO getAvatarVoices(Integer id) {
        List<Fetters> fetters = fettersRepository.findByAvatarIdOrderByTypeAscFetterIdAsc(id);
        Set<String> hashes = new LinkedHashSet<>();
        for (Fetters fetter : fetters) {
            hashes.add(fetter.getVoiceTitleTextMapHash());
            hashes.add(fetter.getVoiceFileTextTextMapHash());
            if (fetter.getTips() != null) {
                hashes.addAll(fetter.getTips());
            }
        }
        Map<String, String> texts = textMapChsUtil.getTexts(hashes);

        List<AvatarVoiceDTO.Daily> dailies = new ArrayList<>();
        List<AvatarVoiceDTO.Battle> battles = new ArrayList<>();
        for (Fetters fetter : fetters) {
            if (fetter.getType() == null) {
                continue;
            }
            List<String> tips = translateTips(fetter.getTips(), texts);
            if (fetter.getType() == 1) {
                AvatarVoiceDTO.Daily daily = new AvatarVoiceDTO.Daily();
                daily.setTitle(texts.get(fetter.getVoiceTitleTextMapHash()));
                daily.setText(texts.get(fetter.getVoiceFileTextTextMapHash()));
                daily.setTips(tips);
                dailies.add(daily);
            } else if (fetter.getType() == 2) {
                AvatarVoiceDTO.Battle battle = new AvatarVoiceDTO.Battle();
                battle.setTitle(texts.get(fetter.getVoiceTitleTextMapHash()));
                battle.setText(texts.get(fetter.getVoiceFileTextTextMapHash()));
                battle.setTips(tips);
                battles.add(battle);
            }
        }

        AvatarVoiceDTO dto = new AvatarVoiceDTO();
        dto.setId(id);
        dto.setDailies(dailies);
        dto.setBattles(battles);
        return dto;
    }

    @Override
    public AvatarConstellationDTO getAvatarConstellations(Integer id) {
        Avatar avatar = avatarRepository.findById(id).orElse(null);
        if (avatar == null) {
            return null;
        }

        List<AvatarConstellationDTO.Constellation> constellations = new ArrayList<>();
        AvatarSkillDepot depot = avatar.getSkillDepotId() == null
                ? null
                : avatarSkillDepotRepository.findById(avatar.getSkillDepotId()).orElse(null);
        if (depot != null && depot.getTalents() != null) {
            List<Integer> talentIds = depot.getTalents().stream()
                    .filter(talentId -> talentId != null && talentId != 0)
                    .toList();
            Map<Integer, AvatarTalent> talents = avatarTalentRepository.findByIdIn(talentIds).stream()
                    .collect(Collectors.toMap(
                            AvatarTalent::getId, talent -> talent, (existing, replacement) -> existing));
            Map<String, String> texts = textMapChsUtil.getTexts(talents.values().stream()
                    .flatMap(talent -> java.util.stream.Stream.of(
                            talent.getNameTextMapHash(), talent.getDescTextMapHash(), talent.getDescExtraTextMapHash()))
                    .toList());

            for (Integer talentId : talentIds) {
                AvatarTalent talent = talents.get(talentId);
                if (talent == null) {
                    continue;
                }
                AvatarConstellationDTO.Constellation constellation = new AvatarConstellationDTO.Constellation();
                constellation.setId(talent.getId());
                constellation.setName(texts.get(talent.getNameTextMapHash()));
                constellation.setDesc(texts.get(talent.getDescTextMapHash()));
                constellation.setDescExtra(texts.get(talent.getDescExtraTextMapHash()));
                constellation.setIconName(talent.getIcon());
                constellations.add(constellation);
            }
        }

        AvatarConstellationDTO dto = new AvatarConstellationDTO();
        dto.setId(id);
        dto.setConstellations(constellations);
        return dto;
    }

    @Override
    public AvatarSkillDTO getAvatarSkills(Integer id) {
        Avatar avatar = avatarRepository.findById(id).orElse(null);
        if (avatar == null) {
            return null;
        }

        List<AvatarSkillDTO.CombatSkill> combatSkills = new ArrayList<>();
        List<AvatarSkillDTO.PassiveSkill> passiveSkills = new ArrayList<>();
        List<AvatarSkillDTO.SpecialProudSkill> specialProudSkills = new ArrayList<>();

        AvatarSkillDepot depot = avatar.getSkillDepotId() == null
                ? null
                : avatarSkillDepotRepository.findById(avatar.getSkillDepotId()).orElse(null);
        if (depot != null) {
            List<Integer> skillIds = new ArrayList<>();
            if (depot.getSkills() != null) {
                for (Integer skillId : depot.getSkills()) {
                    if (skillId == null || skillId == 0) {
                        continue;
                    }
                    if (skillId >= 100000 || EXCLUDED_SKILL_IDS.contains(skillId)) {
                        continue;
                    }
                    skillIds.add(skillId);
                }
            }
            if (depot.getEnergySkill() != null
                    && depot.getEnergySkill() != 0
                    && !skillIds.contains(depot.getEnergySkill())) {
                skillIds.add(depot.getEnergySkill());
            }

            Map<Integer, AvatarSkillRepository.AvatarSkillBriefView> skills =
                    avatarSkillRepository.findBriefByIdIn(skillIds).stream()
                            .filter(skill -> skill.getId() != null)
                            .collect(Collectors.toMap(
                                    AvatarSkillRepository.AvatarSkillBriefView::getId,
                                    skill -> skill,
                                    (existing, replacement) -> existing));

            Set<Integer> groupIds = getIntegers(skills, depot);

            Map<Integer, List<ProudSkill>> proudByGroup = new LinkedHashMap<>();
            if (!groupIds.isEmpty()) {
                for (ProudSkill proudSkill :
                        proudSkillRepository.findByProudSkillGroupIdInOrderByProudSkillGroupIdAscLevelAsc(groupIds)) {
                    if (proudSkill.getProudSkillGroupId() == null) {
                        continue;
                    }
                    proudByGroup
                            .computeIfAbsent(proudSkill.getProudSkillGroupId(), key -> new ArrayList<>())
                            .add(proudSkill);
                }
            }

            Set<String> hashes = new LinkedHashSet<>();
            for (AvatarSkillRepository.AvatarSkillBriefView skill : skills.values()) {
                hashes.add(skill.getNameTextMapHash());
                hashes.add(skill.getDescTextMapHash());
                hashes.add(skill.getExtraDescTextMapHash());
                hashes.add(skill.getDescExtraTextMapHash());
            }
            for (List<ProudSkill> proudSkills : proudByGroup.values()) {
                for (ProudSkill proudSkill : proudSkills) {
                    hashes.add(proudSkill.getNameTextMapHash());
                    hashes.add(proudSkill.getDescTextMapHash());
                    hashes.add(proudSkill.getDescExtraTextMapHash());
                    hashes.add(proudSkill.getLoreDescTextMapHash());
                    if (proudSkill.getParamDescList() != null) {
                        hashes.addAll(proudSkill.getParamDescList());
                    }
                }
            }
            Map<String, String> texts = textMapChsUtil.getTexts(hashes);

            for (Integer skillId : skillIds) {
                AvatarSkillRepository.AvatarSkillBriefView skill = skills.get(skillId);
                if (skill == null) {
                    continue;
                }
                List<ProudSkill> levels = proudByGroup.getOrDefault(skill.getProudSkillGroupId(), List.of());
                AvatarSkillDTO.CombatSkill combatSkill = new AvatarSkillDTO.CombatSkill();
                combatSkill.setId(skill.getId());
                combatSkill.setName(texts.get(skill.getNameTextMapHash()));
                combatSkill.setDesc(texts.get(skill.getDescTextMapHash()));
                combatSkill.setExtraDesc(texts.get(skill.getExtraDescTextMapHash()));
                combatSkill.setDescExtra(texts.get(skill.getDescExtraTextMapHash()));
                combatSkill.setLoreDesc(
                        levels.isEmpty() ? null : texts.get(levels.get(0).getLoreDescTextMapHash()));
                combatSkill.setIconName(skill.getSkillIcon());
                combatSkill.setCombatSkillDataList(buildCombatSkillDataList(levels, texts));
                combatSkills.add(combatSkill);
            }

            if (depot.getInherentProudSkillOpens() != null) {
                for (AvatarSkillDepot.InherentProudSkillOpen open : depot.getInherentProudSkillOpens()) {
                    if (open == null || open.getProudSkillGroupId() == null) {
                        continue;
                    }
                    ProudSkill proudSkill = firstLevelOne(proudByGroup.get(open.getProudSkillGroupId()));
                    if (proudSkill == null || EXCLUDED_PASSIVE_SKILL_IDS.contains(proudSkill.getId())) {
                        continue;
                    }
                    AvatarSkillDTO.PassiveSkill passiveSkill = new AvatarSkillDTO.PassiveSkill();
                    passiveSkill.setId(proudSkill.getId());
                    passiveSkill.setName(texts.get(proudSkill.getNameTextMapHash()));
                    passiveSkill.setDesc(texts.get(proudSkill.getDescTextMapHash()));
                    passiveSkill.setDescExtra(texts.get(proudSkill.getDescExtraTextMapHash()));
                    passiveSkill.setLoreDesc(texts.get(proudSkill.getLoreDescTextMapHash()));
                    passiveSkill.setIconName(proudSkill.getIcon());
                    passiveSkills.add(passiveSkill);
                }
            }

            if (depot.getSpecialProudSkillOpens() != null) {
                for (AvatarSkillDepot.SpecialProudSkillOpen open : depot.getSpecialProudSkillOpens()) {
                    if (open == null || open.getProudSkillGroupId() == null) {
                        continue;
                    }
                    List<ProudSkill> levels = proudByGroup.getOrDefault(open.getProudSkillGroupId(), List.of());
                    ProudSkill proudSkill = firstLevelOne(levels);
                    if (proudSkill == null) {
                        continue;
                    }
                    AvatarSkillDTO.SpecialProudSkill specialProudSkill = new AvatarSkillDTO.SpecialProudSkill();
                    specialProudSkill.setId(proudSkill.getId());
                    specialProudSkill.setName(texts.get(proudSkill.getNameTextMapHash()));
                    specialProudSkill.setDesc(texts.get(proudSkill.getDescTextMapHash()));
                    specialProudSkill.setDescExtra(texts.get(proudSkill.getDescExtraTextMapHash()));
                    specialProudSkill.setLoreDesc(texts.get(proudSkill.getLoreDescTextMapHash()));
                    specialProudSkill.setIconName(proudSkill.getIcon());
                    specialProudSkill.setCombatSkillDataList(buildCombatSkillDataList(levels, texts));
                    specialProudSkills.add(specialProudSkill);
                }
            }
        }

        AvatarSkillDTO dto = new AvatarSkillDTO();
        dto.setId(id);
        dto.setCombatSkills(combatSkills);
        dto.setPassiveSkills(passiveSkills);
        dto.setSpecialProudSkills(specialProudSkills);
        return dto;
    }

    private static Set<Integer> getIntegers(
            Map<Integer, AvatarSkillRepository.AvatarSkillBriefView> skills, AvatarSkillDepot depot) {
        Set<Integer> groupIds = new LinkedHashSet<>();
        for (AvatarSkillRepository.AvatarSkillBriefView skill : skills.values()) {
            if (skill.getProudSkillGroupId() != null) {
                groupIds.add(skill.getProudSkillGroupId());
            }
        }
        if (depot.getInherentProudSkillOpens() != null) {
            for (AvatarSkillDepot.InherentProudSkillOpen open : depot.getInherentProudSkillOpens()) {
                if (open != null && open.getProudSkillGroupId() != null) {
                    groupIds.add(open.getProudSkillGroupId());
                }
            }
        }
        if (depot.getSpecialProudSkillOpens() != null) {
            for (AvatarSkillDepot.SpecialProudSkillOpen open : depot.getSpecialProudSkillOpens()) {
                if (open != null && open.getProudSkillGroupId() != null) {
                    groupIds.add(open.getProudSkillGroupId());
                }
            }
        }
        return groupIds;
    }

    private List<AvatarSkillDTO.CombatSkillData> buildCombatSkillDataList(
            List<ProudSkill> proudSkills, Map<String, String> texts) {
        List<AvatarSkillDTO.CombatSkillData> result = new ArrayList<>();
        for (ProudSkill proudSkill : proudSkills) {
            AvatarSkillDTO.CombatSkillData data = new AvatarSkillDTO.CombatSkillData();
            data.setLevel(proudSkill.getLevel());
            data.setParamList(proudSkill.getParamList());
            List<String> paramDescList = new ArrayList<>();
            if (proudSkill.getParamDescList() != null) {
                for (String hash : proudSkill.getParamDescList()) {
                    String text = texts.get(hash);
                    if (text != null) {
                        paramDescList.add(text);
                    }
                }
            }
            data.setParamDescList(paramDescList);
            result.add(data);
        }
        return result;
    }

    private static ProudSkill firstLevelOne(List<ProudSkill> proudSkills) {
        if (proudSkills == null) {
            return null;
        }
        for (ProudSkill proudSkill : proudSkills) {
            if (proudSkill.getLevel() != null && proudSkill.getLevel() == 1) {
                return proudSkill;
            }
        }
        return null;
    }

    private static List<String> translateTips(List<String> hashes, Map<String, String> texts) {
        List<String> tips = new ArrayList<>();
        if (hashes == null) {
            return tips;
        }
        for (String hash : hashes) {
            String text = texts.get(hash);
            if (text != null) {
                tips.add(text);
            }
        }
        return tips;
    }

    private Map<Integer, AvatarCurve> avatarCurves() {
        Map<Integer, AvatarCurve> curves = avatarCurveCache;
        if (curves == null) {
            synchronized (this) {
                curves = avatarCurveCache;
                if (curves == null) {
                    curves = avatarCurveRepository.findAll().stream()
                            .filter(curve -> curve.getLevel() != null)
                            .collect(Collectors.toMap(
                                    AvatarCurve::getLevel, curve -> curve, (existing, replacement) -> existing));
                    avatarCurveCache = curves;
                }
            }
        }
        return curves;
    }

    private static double curveValue(AvatarCurve curve, String type) {
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

    private static String toGachaAvatarIcon(String icon) {
        if (icon == null) {
            return null;
        }
        return "UI_Gacha_AvatarIcon_" + stripPrefix(icon, "UI_AvatarIcon_");
    }

    private static String toGachaAvatarImage(String icon) {
        if (icon == null) {
            return null;
        }
        return "UI_Gacha_AvatarImg_" + stripPrefix(icon, "UI_AvatarIcon_");
    }

    private static String toCostumeImage(String frontIconName) {
        if (frontIconName == null) {
            return null;
        }
        return "UI_Costume_" + stripPrefix(frontIconName, "UI_AvatarIcon_");
    }

    private static String stripPrefix(String value, String prefix) {
        if (value == null) {
            return null;
        }
        return value.startsWith(prefix) ? value.substring(prefix.length()) : value;
    }

    private static String orDefault(String value, String defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : value;
    }

    private static String toVisionIconName(String assocType, String arkheType) {
        if (assocType == null || assocType.isEmpty()) {
            return null;
        }
        String key = stripPrefix(assocType, ASSOC_PREFIX);
        return switch (key) {
            case "MAINACTOR" -> "UI_Img_Pendant_Player";
            case "SNEZHNAYA_STAR" -> "UI_Img_Pendant_StellaVision";
            case "FONTAINE" -> {
                String arkhe = "Furina".equals(arkheType) ? "Pneuma" : arkheType;
                if (arkhe == null || arkhe.isEmpty()) {
                    yield "UI_Img_Pendant_Fontaine";
                }
                yield "UI_Img_Pendant_Fontaine_" + capitalize(arkhe);
            }
            default -> "UI_Img_Pendant_" + titleCase(key);
        };
    }

    private static String toElementIconName(String assocType, String element) {
        if (element == null || "Unknown".equals(element)) {
            return null;
        }
        String key = stripPrefix(assocType, ASSOC_PREFIX);
        String prefix =
                switch (key) {
                    case "FATUI" -> "Fatui";
                    case "LIYUE" -> "Liyue";
                    case "MAINACTOR" -> "Player";
                    case "RANGER" -> "Ranger";
                    default -> "Common";
                };
        return "UI_Icon_" + prefix + "_" + element;
    }

    private static String titleCase(String value) {
        String[] parts = value.split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            if (!builder.isEmpty()) {
                builder.append('_');
            }
            builder.append(part.substring(0, 1).toUpperCase(Locale.ROOT));
            builder.append(part.substring(1).toLowerCase(Locale.ROOT));
        }
        return builder.toString();
    }

    private static String capitalize(String value) {
        if (value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase(Locale.ROOT) + value.substring(1);
    }

    private static String translateElement(String element) {
        if (element == null) {
            return null;
        }
        return switch (element) {
            case "火" -> "Fire";
            case "水" -> "Water";
            case "冰" -> "Ice";
            case "雷" -> "Electric";
            case "风" -> "Wind";
            case "岩" -> "Rock";
            case "草" -> "Grass";
            default -> element;
        };
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
