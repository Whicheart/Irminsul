package com.genshin.irminsul.service.impl;

import com.genshin.irminsul.dto.monster.MonsterList;
import com.genshin.irminsul.entity.AnimalCodex;
import com.genshin.irminsul.entity.monster.MonsterDescribe;
import com.genshin.irminsul.repository.AnimalCodexRepository;
import com.genshin.irminsul.repository.monster.MonsterDescribeRepository;
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

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {
    private static final String CODEX_TYPE_MONSTER = "CODEX_MONSTER";
    private static final String CODEX_SUBTYPE_ELEMENTAL = "CODEX_SUBTYPE_ELEMENTAL";

    private final AnimalCodexRepository animalCodexRepository;
    private final MonsterDescribeRepository monsterDescribeRepository;
    private final TextMapChsUtil textMapChsUtil;

    // 排除，可以考虑填入 25010101, 25020101, 25030101, 25040101
    private static final Set<Integer> EXCLUDED_MONSTER_IDS = Set.of();

    @Override
    public List<MonsterList.MonsterBrief> getMonsterList(String name, List<String> types) {
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

        List<MonsterList.MonsterBrief> result = new ArrayList<>();
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

            MonsterList.MonsterBrief brief = new MonsterList.MonsterBrief();
            brief.setId(codex.getId());
            brief.setName(monsterName);
            brief.setType(codexType);
            brief.setIconName(describe == null ? null : describe.getIcon());
            result.add(brief);
        }
        return result;
    }
}
