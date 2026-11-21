package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.monster.MonsterList;
import java.util.List;

public interface MonsterService {
    List<MonsterList.MonsterBrief> getMonsterList(String name, List<String> types);
}
