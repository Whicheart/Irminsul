package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.monster.MonsterBaseInfoDTO;
import com.genshin.irminsul.dto.monster.MonsterList;
import java.util.List;

/**
 * 敌对生物业务接口
 */
public interface MonsterService {
    /**
     * 查询敌对生物列表
     *
     * @param name  名称关键词，可选
     * @param types 图鉴子类型，可选；为空返回全部
     * @return 简要生物列表
     */
    List<MonsterList.MonsterBrief> getMonsterList(String name, List<String> types);

    /**
     * 查询敌对生物基础信息
     *
     * @param id 生物 ID
     * @return 生物详情；不存在时返回 null
     */
    MonsterBaseInfoDTO getMonsterBaseInfo(Integer id);
}
