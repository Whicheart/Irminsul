package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.weapon.WeaponBaseInfoResponse;
import com.genshin.irminsul.dto.weapon.WeaponListResponse;
import com.genshin.irminsul.service.WeaponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 武器接口
 *
 * <p>列表可按名称、类型与星级筛选；详情接口在武器不存在时返回 404
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weapon")
public class WeaponController {

    // 武器业务逻辑，由构造器注入
    private final WeaponService weaponService;

    /**
     * 武器列表，按图鉴顺序倒序返回
     *
     * @param name        名称关键词，可选，仅做包含匹配
     * @param weaponTypes 武器类型，可选
     * @param ranks       星级，可选
     * @return 简要武器列表
     */
    @GetMapping("/list")
    public Result<List<WeaponListResponse.WeaponBrief>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> weaponTypes,
            @RequestParam(required = false) List<Integer> ranks) {
        return Result.ok(weaponService.getWeaponList(name, weaponTypes, ranks));
    }

    /**
     * 武器基础信息，包含攻击成长、副属性、技能、材料与故事
     *
     * @param id 武器 ID
     * @return 武器详情；不存在时返回 404 错误响应
     */
    @GetMapping("/{id}/base-info")
    public Result<WeaponBaseInfoResponse> getBaseInfo(@PathVariable Integer id) {
        WeaponBaseInfoResponse detail = weaponService.getWeaponBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "武器不存在");
        }
        return Result.ok(detail);
    }
}
