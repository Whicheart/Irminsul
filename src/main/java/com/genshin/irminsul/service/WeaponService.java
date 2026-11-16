package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.weapon.WeaponBaseInfoDTO;
import com.genshin.irminsul.dto.weapon.WeaponList;
import java.util.List;

public interface WeaponService {

    List<WeaponList.WeaponBrief> getWeaponList(String name, List<String> weaponTypes, List<Integer> ranks);

    WeaponBaseInfoDTO getWeaponBaseInfo(Integer id);
}
