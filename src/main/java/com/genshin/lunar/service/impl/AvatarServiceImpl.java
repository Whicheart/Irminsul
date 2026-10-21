package com.genshin.lunar.service.impl;

import com.genshin.lunar.dto.AvatarBaseInfoDTO;
import com.genshin.lunar.dto.AvatarList;
import com.genshin.lunar.entity.Avatar;
import com.genshin.lunar.entity.AvatarCodex;
import com.genshin.lunar.entity.FetterInfo;
import com.genshin.lunar.repository.AvatarCodexRepository;
import com.genshin.lunar.repository.AvatarRepository;
import com.genshin.lunar.repository.FetterInfoRepository;
import com.genshin.lunar.service.AvatarService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.genshin.lunar.util.TextMapChsUtil;
import org.springframework.stereotype.Service;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final AvatarCodexRepository avatarCodexRepository;
    private final FetterInfoRepository fetterInfoRepository;
    private final TextMapChsUtil textMapChsUtil;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             AvatarCodexRepository avatarCodexRepository,
                             FetterInfoRepository fetterInfoRepository,
                             TextMapChsUtil textMapChsUtil) {
        this.avatarRepository = avatarRepository;
        this.avatarCodexRepository = avatarCodexRepository;
        this.fetterInfoRepository = fetterInfoRepository;
        this.textMapChsUtil = textMapChsUtil;
    }

    @Override
    public List<AvatarList.AvatarBrief> getAvatarList(){
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
            if (avatar.getId() < 10000002 || avatar.getId() > 10000899) {
                continue;
            }

            AvatarList.AvatarBrief brief = new AvatarList.AvatarBrief();

            brief.setId(avatar.getId());
            brief.setName(textMapChsUtil.getText(avatar.getNameTextMapHash()));
            brief.setQualityType(translateQuality(avatar.getQualityType()));
            brief.setWeaponType(translateWeapon(avatar.getWeaponType()));

            AvatarCodex codex = codexMap.get(avatar.getId());
            if (codex != null) {
                brief.setSortId(codex.getSortId());
            }

            FetterInfo info = infoMap.get(avatar.getId());
            if (info != null) {
                brief.setElement(textMapChsUtil.getText(info.getAvatarVisionBeforTextMapHash()));
            }

            result.add(brief);
        }

        result.sort((a, b) -> {
            Integer s1 = a.getSortId() == null ? 99999 : a.getSortId();
            Integer s2 = b.getSortId() == null ? 99999 : b.getSortId();
            return s1.compareTo(s2);
        });

        return result;
    }

    @Override
    public AvatarBaseInfoDTO getAvatarBaseInfo(Integer id) {
        Avatar avatar = avatarRepository.findById(id).orElse(null);
        if (avatar == null) return null;

        FetterInfo fetterInfo = fetterInfoRepository.findById(id).orElse(new FetterInfo());
        AvatarCodex avatarCodex = avatarCodexRepository.findById(id).orElse(new AvatarCodex());

        AvatarBaseInfoDTO avatarBaseInfoDTO = new AvatarBaseInfoDTO();
        avatarBaseInfoDTO.setId(avatar.getId());

        avatarBaseInfoDTO.setName(textMapChsUtil.getText(avatar.getNameTextMapHash()));
        avatarBaseInfoDTO.setDesc(textMapChsUtil.getText(avatar.getDescTextMapHash()));
        avatarBaseInfoDTO.setTitle(textMapChsUtil.getText(fetterInfo.getAvatarTitleTextMapHash()));
        avatarBaseInfoDTO.setAffiliation(textMapChsUtil.getText(fetterInfo.getAvatarNativeTextMapHash()));

        avatarBaseInfoDTO.setQualityType(translateQuality(avatar.getQualityType()));
        avatarBaseInfoDTO.setWeaponType(translateWeapon(avatar.getWeaponType()));
        avatarBaseInfoDTO.setReleaseTime(avatarCodex.getBeginTime());

        AvatarBaseInfoDTO.Birth birth = new AvatarBaseInfoDTO.Birth();
        birth.setMonth(fetterInfo.getInfoBirthMonth());
        birth.setDay(fetterInfo.getInfoBirthDay());
        avatarBaseInfoDTO.setBirth(birth);

        AvatarBaseInfoDTO.Cv cv = new AvatarBaseInfoDTO.Cv();
        cv.setCn(textMapChsUtil.getText(fetterInfo.getCvChineseTextMapHash()));
        cv.setEn(textMapChsUtil.getText(fetterInfo.getCvEnglishTextMapHash()));
        cv.setJp(textMapChsUtil.getText(fetterInfo.getCvJapaneseTextMapHash()));
        cv.setKr(textMapChsUtil.getText(fetterInfo.getCvKoreanTextMapHash()));
        avatarBaseInfoDTO.setCv(cv);

        avatarBaseInfoDTO.setHp(java.util.List.of(avatar.getHpBase()));
        avatarBaseInfoDTO.setAtk(java.util.List.of(avatar.getAttackBase()));
        avatarBaseInfoDTO.setDef(java.util.List.of(avatar.getDefenseBase()));

        return avatarBaseInfoDTO;
    }

    private String translateQuality(String quality) {
        if (quality == null) return "未知";
        return switch (quality) {
            case "QUALITY_ORANGE" -> "五星";
            case "QUALITY_ORANGE_SP" -> "五星"; // 埃洛伊等特殊五星
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
            case "WEAPON_CROSSBOW" -> "弩"; // 旅行者特殊形态
            default -> weapon;
        };
    }

    //    private final MongoTemplate mongoTemplate;
//    public AvatarServiceImpl(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }

//    @Override
//    public List<Map<String, Object>> searchAvatar(AvatarList query) {
//        Criteria criteria = new Criteria();
//
//        // 隐藏 beta 条目
//        criteria.and("beta").ne(true);
//
//        if (query.getName() != null && !query.getName().isEmpty()) {
//            criteria.and("name").regex(".*" + query.getName() + ".*", "i");
//        }
//
//        Query mongoQuery = new Query(criteria);
//        mongoQuery.with(
//                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "_id"));
//
//        List<org.bson.Document> docs = mongoTemplate.find(mongoQuery, org.bson.Document.class, "avatar");
//        List<Map<String, Object>> result = new ArrayList<>();
//        for (org.bson.Document doc : docs) {
//            Map<String, Object> map = new HashMap<>();
//            map.putAll(doc);
//            map.put("id", doc.get("_id"));
//            map.remove("_id");
//            result.add(map);
//        }
//        return result;
//    }

//    @Override
//    public List<Map<String, Object>> searchAvatarSimple(AvatarList query) {
//        Criteria criteria = new Criteria();
//
//        // 隐藏 beta 条目
//        criteria.and("beta").ne(true);
//
//        if (query.getName() != null && !query.getName().isEmpty()) {
//            criteria.and("name").regex(".*" + query.getName() + ".*", "i");
//        }
//        if (query.getStar() != null && !query.getStar().isEmpty()) {
//            criteria.and("star").in(query.getStar());
//        }
//        if (query.getElem() != null && !query.getElem().isEmpty()) {
//            criteria.and("elem").in(query.getElem());
//        }
//        if (query.getWeapon() != null && !query.getWeapon().isEmpty()) {
//            criteria.and("weapon").in(query.getWeapon());
//        }
//
//        Query mongoQuery = new Query(criteria);
//        mongoQuery.with(
//                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "_id"));
//
//        // 只查询需要的字段：_id, name, star, elem, weapon
//        mongoQuery.fields().include("name", "star", "elem", "weapon");
//
//        List<org.bson.Document> docs = mongoTemplate.find(mongoQuery, org.bson.Document.class, "avatar");
//        List<Map<String, Object>> result = new ArrayList<>();
//        for (org.bson.Document doc : docs) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("name", doc.get("name"));
//            map.put("elem", doc.get("elem"));
//            map.put("weapon", doc.get("weapon"));
//            result.add(map);
//        }
//        return result;
//    }
}
