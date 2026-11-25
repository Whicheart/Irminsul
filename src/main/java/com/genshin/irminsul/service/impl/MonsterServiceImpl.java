package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.monster.MonsterBaseInfoResponse;
import com.genshin.irminsul.dto.monster.MonsterListResponse;
import com.genshin.irminsul.entity.AnimalCodex;
import com.genshin.irminsul.entity.monster.Monster;
import com.genshin.irminsul.entity.monster.MonsterCurve;
import com.genshin.irminsul.entity.monster.MonsterDescribe;
import com.genshin.irminsul.entity.monster.MonsterSpecialName;
import com.genshin.irminsul.entity.monster.MonsterTitle;
import com.genshin.irminsul.repository.AnimalCodexRepository;
import com.genshin.irminsul.repository.monster.MonsterCurveRepository;
import com.genshin.irminsul.repository.monster.MonsterDescribeRepository;
import com.genshin.irminsul.repository.monster.MonsterRepository;
import com.genshin.irminsul.repository.monster.MonsterSpecialNameRepository;
import com.genshin.irminsul.repository.monster.MonsterTitleRepository;
import com.genshin.irminsul.service.MonsterService;
import com.genshin.irminsul.util.TextMapChsUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 敌对生物业务实现
 *
 * <p>以 animal_codex、monster、monster_describe 为主链路，血量/攻击/防御按
 * 基础值乘成长曲线计算，并分别用 MHP/MATK 曲线封顶
 */
@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {
    private static final String CODEX_TYPE_MONSTER = "CODEX_MONSTER";
    private static final String CODEX_SUBTYPE_ELEMENTAL = "CODEX_SUBTYPE_ELEMENTAL";

    private static final String PROP_BASE_HP = "FIGHT_PROP_BASE_HP";
    private static final String PROP_BASE_ATTACK = "FIGHT_PROP_BASE_ATTACK";
    private static final String PROP_BASE_DEFENSE = "FIGHT_PROP_BASE_DEFENSE";

    private static final String CURVE_HP = "GROW_CURVE_HP";
    private static final String CURVE_ATTACK = "GROW_CURVE_ATTACK";
    private static final String CURVE_DEFENSE = "GROW_CURVE_DEFENSE";

    private static final String CURVE_MHP = "GROW_CURVE_MHP";
    private static final String CURVE_MATK = "GROW_CURVE_MATK";

    // 生物图鉴仓储，提供列表与排序
    private final AnimalCodexRepository animalCodexRepository;
    // 生物数值仓储
    private final MonsterRepository monsterRepository;
    // 生物描述仓储，提供称号与特殊名
    private final MonsterDescribeRepository monsterDescribeRepository;
    // 生物称号仓储
    private final MonsterTitleRepository monsterTitleRepository;
    // 生物特殊名仓储
    private final MonsterSpecialNameRepository monsterSpecialNameRepository;
    // 生物成长曲线仓储
    private final MonsterCurveRepository monsterCurveRepository;
    // 文本哈希解析工具
    private final TextMapChsUtil textMapChsUtil;

    // 排除，可以考虑填入 25010101, 25020101, 25030101, 25040101
    private static final Set<Integer> EXCLUDED_MONSTER_IDS = Set.of();

    // 成长曲线缓存，整表只读，双重检查锁初始化
    private volatile List<MonsterCurve> curveCache;

    /**
     * 查询敌对生物列表
     *
     * <p>以动物图鉴为源，按图鉴排序号升序
     *
     * @param name  名称关键词，可选
     * @param types 图鉴子类型，可选；为空返回全部
     * @return 敌对生物简要列表
     */
    @Override
    public List<MonsterListResponse.MonsterBrief> getMonsterList(String name, List<String> types) {
        // 以图鉴条目为数据源，按 sortOrder 升序排列
        List<AnimalCodex> codexes = animalCodexRepository.findAll().stream()
                .filter(codex -> CODEX_TYPE_MONSTER.equals(codex.getType()))
                .filter(codex -> !EXCLUDED_MONSTER_IDS.contains(codex.getId()))
                .sorted(Comparator.comparing(
                        AnimalCodex::getSortOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
        if (codexes.isEmpty()) {
            return List.of();
        }

        // 批量取图鉴描述
        Set<Integer> describeIds = codexes.stream()
                .map(AnimalCodex::getDescribeId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Integer, MonsterDescribe> describeMap = new HashMap<>();
        for (MonsterDescribe describe : monsterDescribeRepository.findAllById(describeIds)) {
            describeMap.put(describe.getId(), describe);
        }

        // 批量翻译名称
        Set<String> nameHashes = describeMap.values().stream()
                .map(MonsterDescribe::getNameTextMapHash)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<String, String> names = textMapChsUtil.getTexts(nameHashes);

        String nameKeyword = name == null ? null : name.trim();
        Set<String> typeFilter = types == null
                ? new HashSet<>()
                : types.stream()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(value -> !value.isEmpty())
                        .collect(Collectors.toSet());

        List<MonsterListResponse.MonsterBrief> result = new ArrayList<>();
        for (AnimalCodex codex : codexes) {
            MonsterDescribe describe = describeMap.get(codex.getDescribeId());
            String monsterName = describe == null ? null : names.get(describe.getNameTextMapHash());
            if (nameKeyword != null
                    && !nameKeyword.isEmpty()
                    && (monsterName == null || !monsterName.contains(nameKeyword))) {
                continue;
            }

            String codexType = codex.getSubType() == null ? CODEX_SUBTYPE_ELEMENTAL : codex.getSubType();
            if (!typeFilter.isEmpty() && !typeFilter.contains(codexType)) {
                continue;
            }

            MonsterListResponse.MonsterBrief brief = new MonsterListResponse.MonsterBrief();
            brief.setId(codex.getId());
            brief.setName(monsterName);
            brief.setType(codexType);
            brief.setIconName(describe == null ? null : describe.getIcon());
            result.add(brief);
        }
        return result;
    }

    /**
     * 组装敌对生物基础信息
     *
     * <p>先经图鉴取描述与称号，再按成长曲线逐级计算生命、攻击与防御；数据缺失时返回 null
     *
     * @param id 敌对生物 ID
     * @return 详情，不存在时为 null
     */
    @Override
    public MonsterBaseInfoResponse getMonsterBaseInfo(Integer id) {

        MonsterBaseInfoResponse dto = new MonsterBaseInfoResponse();
        dto.setId(id);

        // 22110401、23080101、29091001、29091101 在 monster 表中不存在。处理方式：个人倾向于直接返回 null
        // 29010101、29010103 的 propGrowCurves 字段为空数组。处理方式：个人倾向于采用默认的 GROW_CURVE_HP/ATTACK/DEFENSE 值进行兜底处理
        AnimalCodex codex = animalCodexRepository.findById(id).orElse(null);
        if (codex == null) {
            return null;
        }
        Monster monster = monsterRepository.findById(id).orElse(null);
        if (monster == null) {
            return null;
        }

        MonsterDescribe describe = null;
        if (codex.getDescribeId() != null) {
            describe = monsterDescribeRepository.findById(codex.getDescribeId()).orElse(null);
        }
        MonsterTitle title = null;
        if (describe != null && describe.getTitleID() != null) {
            title = monsterTitleRepository.findById(describe.getTitleID()).orElse(null);
        }
        List<MonsterSpecialName> specialNames = List.of();
        if (describe != null && describe.getSpecialNameLabID() != null) {
            specialNames =
                    monsterSpecialNameRepository.findBySpecialNameLabIDOrderByIdAsc(describe.getSpecialNameLabID());
        }

        Set<String> hashes = new HashSet<>();
        if (title != null && title.getTitleNameTextMapHash() != null) {
            hashes.add(title.getTitleNameTextMapHash());
        }
        if (describe != null && describe.getNameTextMapHash() != null) {
            hashes.add(describe.getNameTextMapHash());
        }
        for (MonsterSpecialName specialName : specialNames) {
            if (specialName.getSpecialNameTextMapHash() != null) {
                hashes.add(specialName.getSpecialNameTextMapHash());
            }
        }
        if (codex.getDescTextMapHash() != null) {
            hashes.add(codex.getDescTextMapHash());
        }
        if (codex.getExtraDescTextMapHash() != null) {
            hashes.addAll(codex.getExtraDescTextMapHash());
        }
        Map<String, String> texts = textMapChsUtil.getTexts(hashes);

        // DTO.title <= 先用传入的 id 参数找到 AnimalCodex.describeId，然后拿着 AnimalCodex.describeId 找到 MonsterDescribe.titleID，最后用
        // MonsterDescribe.titleID 在 MonsterTitle 里找到 MonsterTitle.titleNameTextMapHash 并解出
        // DTO.name <= 先用传入的 id 参数找到 AnimalCodex.describeId，然后拿着 AnimalCodex.describeId 找到
        // MonsterDescribe.nameTextMapHash 并解出
        // DTO.specialName <= 先用传入的 id 参数找到 AnimalCodex.describeId，然后拿着 AnimalCodex.describeId 找到
        // MonsterDescribe.specialNameLabID，最后用 MonsterDescribe.specialNameLabID 找到
        // MonsterSpecialName.specialNameTextMapHash 并解出。注：由于 MonsterDescribe.specialNameLabID 是组名，存在多个对应的
        // MonsterSpecialName.specialNameTextMapHash，所以这里要把它们全部取过来
        // DTO.iconName <= 先用传入的 id 参数找到 AnimalCodex.describeId，然后拿着 AnimalCodex.describeId 找到 MonsterDescribe.icon
        dto.setTitle(title == null ? null : texts.get(title.getTitleNameTextMapHash()));
        dto.setName(describe == null ? null : texts.get(describe.getNameTextMapHash()));
        List<String> specialNameTexts = new ArrayList<>();
        for (MonsterSpecialName specialName : specialNames) {
            String text = texts.get(specialName.getSpecialNameTextMapHash());
            if (text != null) {
                specialNameTexts.add(text);
            }
        }
        dto.setSpecialName(specialNameTexts);
        dto.setIconName(describe == null ? null : describe.getIcon());

        // DTO.description <= 用传入的 id 参数找到 AnimalCodex.descTextMapHash 和 AnimalCodex.extraDescTextMapHash，如果
        // AnimalCodex.extraDescTextMapHash 可解就用它，否则就用 AnimalCodex.descTextMapHash
        List<String> description = new ArrayList<>();
        if (codex.getExtraDescTextMapHash() != null) {
            for (String hash : codex.getExtraDescTextMapHash()) {
                String text = texts.get(hash);
                if (text != null) {
                    description.add(text);
                }
            }
        }
        if (description.isEmpty() && codex.getDescTextMapHash() != null) {
            String text = texts.get(codex.getDescTextMapHash());
            if (text != null) {
                description.add(text);
            }
        }
        dto.setDescription(description);

        // DTO.hp/attack/defense <= 先用传入的 id 参数找到 Monster.hpBase/attackBase/defenseBase 字段，然后根据 Monster.propGrowCurves
        // 字段去 MonsterCurve 里找到对应的 curve 乘上去，MonsterCurve 有多少级，这个数组就应该有多少元素。对于 hp/atk 要注意 MonsterCurve 里的
        // GROW_CURVE_MHP/MATK 字段的限制
        dto.setHp(buildCurveValues(monster.getHpBase(), findCurveName(monster, PROP_BASE_HP, CURVE_HP), CURVE_MHP));
        dto.setAttack(buildCurveValues(
                monster.getAttackBase(), findCurveName(monster, PROP_BASE_ATTACK, CURVE_ATTACK), CURVE_MATK));
        dto.setDefense(buildCurveValues(
                monster.getDefenseBase(), findCurveName(monster, PROP_BASE_DEFENSE, CURVE_DEFENSE), null));

        // DTO.*SubHurt <= 用传入的 id 参数找到 Monster.*SubHurt 字段
        dto.setFireSubHurt(monster.getFireSubHurt());
        dto.setWaterSubHurt(monster.getWaterSubHurt());
        dto.setIceSubHurt(monster.getIceSubHurt());
        dto.setElecSubHurt(monster.getElecSubHurt());
        dto.setWindSubHurt(monster.getWindSubHurt());
        dto.setRockSubHurt(monster.getRockSubHurt());
        dto.setGrassSubHurt(monster.getGrassSubHurt());
        dto.setPhysicalSubHurt(monster.getPhysicalSubHurt());

        return dto;
    }

    /**
     * 在怪物的成长曲线配置中查找属性对应的曲线名，找不到时使用兜底值
     */
    private String findCurveName(Monster monster, String propType, String fallback) {
        if (monster.getPropGrowCurves() != null) {
            for (Monster.PropGrowCurve propGrowCurve : monster.getPropGrowCurves()) {
                if (propGrowCurve != null
                        && propType.equals(propGrowCurve.getType())
                        && propGrowCurve.getGrowCurve() != null
                        && !propGrowCurve.getGrowCurve().isEmpty()) {
                    return propGrowCurve.getGrowCurve();
                }
            }
        }
        return fallback;
    }

    /**
     * 按成长曲线逐级计算数值，可选上限曲线用于封顶
     *
     * @param base      基础值
     * @param curveName 属性成长曲线名
     * @param capName   上限曲线名，可为 null
     * @return 逐级数值，顺序与曲线表一致
     */
    private List<Double> buildCurveValues(Double base, String curveName, String capName) {
        if (base == null) {
            return List.of();
        }
        List<Double> values = new ArrayList<>();
        for (MonsterCurve monsterCurve : getCurveCache()) {
            Map<String, Double> curves = monsterCurve.getCurves();
            Double coefficient = curves == null ? null : curves.get(curveName);
            if (coefficient == null) {
                values.add(0.0);
                continue;
            }
            if (capName != null && curves.get(capName) != null) {
                coefficient = Math.min(coefficient, curves.get(capName));
            }
            values.add(base * coefficient);
        }
        return values;
    }

    /**
     * 懒加载全部怪物成长曲线，使用双重检查锁保证只查库一次
     */
    private List<MonsterCurve> getCurveCache() {
        List<MonsterCurve> cache = curveCache;
        if (cache == null) {
            synchronized (this) {
                cache = curveCache;
                if (cache == null) {
                    cache = monsterCurveRepository.findAll().stream()
                            .sorted(Comparator.comparing(MonsterCurve::getId))
                            .toList();
                    curveCache = cache;
                }
            }
        }
        return cache;
    }
}
