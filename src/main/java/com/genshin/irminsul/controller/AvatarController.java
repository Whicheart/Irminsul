package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.avatar.*;
import com.genshin.irminsul.service.AvatarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 角色接口
 *
 * <p>列表接口支持按名称、元素、武器与品质筛选；详情接口在角色不存在时返回 404
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/avatar")
public class AvatarController {

    // 角色业务逻辑，由构造器注入
    private final AvatarService avatarService;

    /**
     * 角色列表，按图鉴顺序倒序返回
     *
     * @param name         名称关键词，可选，仅做包含匹配
     * @param elements     元素类型，可选
     * @param weaponTypes  武器类型，可选
     * @param qualityTypes 品质，可选
     * @return 简要角色列表，不含详情
     */
    @GetMapping("/list")
    public Result<List<AvatarList.AvatarBrief>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> elements,
            @RequestParam(required = false) List<String> weaponTypes,
            @RequestParam(required = false) List<String> qualityTypes) {
        return Result.ok(avatarService.getAvatarList(name, elements, weaponTypes, qualityTypes));
    }

    /**
     * 角色基础信息，包含属性成长、突破材料与图像资源
     *
     * @param id 角色 ID
     * @return 角色详情；不存在时返回 404 错误响应
     */
    @GetMapping("/{id}/base-info")
    public Result<AvatarBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        AvatarBaseInfoDTO detail = avatarService.getAvatarBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "角色不存在");
        }
        return Result.ok(detail);
    }

    /**
     * 角色装扮列表，默认时装排在首位
     *
     * @param id 角色 ID
     * @return 装扮数据
     */
    @GetMapping("/{id}/outfits")
    public Result<AvatarOutfitDTO> getAvatarOutfits(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarOutfits(id));
    }

    /**
     * 角色资料故事
     *
     * @param id 角色 ID
     * @return 故事数据
     */
    @GetMapping("/{id}/stories")
    public Result<AvatarStoryDTO> getAvatarStories(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarStories(id));
    }

    /**
     * 角色语音，按日常与战斗分组
     *
     * @param id 角色 ID
     * @return 语音数据
     */
    @GetMapping("/{id}/voices")
    public Result<AvatarVoiceDTO> getAvatarVoices(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarVoices(id));
    }

    /**
     * 角色命座
     *
     * @param id 角色 ID
     * @return 命座数据；角色不存在时为 null
     */
    @GetMapping("/{id}/constellations")
    public Result<AvatarConstellationDTO> getAvatarConstellations(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarConstellations(id));
    }

    /**
     * 角色技能，包含主动、固有与特殊天赋
     *
     * @param id 角色 ID
     * @return 技能数据；角色不存在时为 null
     */
    @GetMapping("/{id}/skills")
    public Result<AvatarSkillDTO> getAvatarSkills(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarSkills(id));
    }
}
