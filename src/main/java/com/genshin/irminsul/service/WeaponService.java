package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.weapon.WeaponBaseInfoDTO;
import com.genshin.irminsul.dto.weapon.WeaponList;
import java.util.List;

/**
 * 武器业务接口
 */
public interface WeaponService {

    /**
     * 查询武器列表
     *
     * @param name        名称关键词，可选
     * @param weaponTypes 武器类型，可选
     * @param ranks       星级，可选
     * @return 简要武器列表，按图鉴顺序倒序
     */
    List<WeaponList.WeaponBrief> getWeaponList(String name, List<String> weaponTypes, List<Integer> ranks);

    /**
     * 查询武器基础信息
     *
     * @param id 武器 ID
     * @return 武器详情；不存在时返回 null
     */
    WeaponBaseInfoDTO getWeaponBaseInfo(Integer id);
}
