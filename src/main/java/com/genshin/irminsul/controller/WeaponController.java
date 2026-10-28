package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.WeaponBaseInfoDTO;
import com.genshin.irminsul.dto.WeaponList;
import com.genshin.irminsul.service.WeaponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weapon")
public class WeaponController {
    private final WeaponService weaponService;

    @GetMapping("/list")
    public Result<List<WeaponList.WeaponBrief>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> weaponTypes,
            @RequestParam(required = false) List<Integer> ranks) {
        return Result.ok(weaponService.getWeaponList(name, weaponTypes, ranks));
    }

    @GetMapping("/{id}/base-info")
    public Result<WeaponBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        WeaponBaseInfoDTO detail = weaponService.getWeaponBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "武器不存在");
        }
        return Result.ok(detail);
    }
}
