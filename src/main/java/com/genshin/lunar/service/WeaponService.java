package com.genshin.lunar.service;

import com.genshin.lunar.dto.WeaponQuery;
import java.util.List;
import java.util.Map;

public interface WeaponService {
    List<Map<String, Object>> searchWeapon(WeaponQuery query);

    List<Map<String, Object>> searchWeaponSimple(WeaponQuery query);
}
