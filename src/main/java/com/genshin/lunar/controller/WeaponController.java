package com.genshin.lunar.controller;

import com.genshin.lunar.common.Result;
import com.genshin.lunar.dto.WeaponQuery;
import com.genshin.lunar.service.WeaponService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weapon")
public class WeaponController {
    private final WeaponService weaponService;

    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }

    @GetMapping("/search")
    public Result<List<Map<String, Object>>> searchWeapon(WeaponQuery query) {
        return Result.ok(weaponService.searchWeapon(query));
    }

    @GetMapping("/simple_search")
    public Result<List<Map<String, Object>>> searchSimple(WeaponQuery query) {
        return Result.ok(weaponService.searchWeaponSimple(query));
    }
}
