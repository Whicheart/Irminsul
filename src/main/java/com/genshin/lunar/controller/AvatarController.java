package com.genshin.lunar.controller;

import com.genshin.lunar.common.Result;
import com.genshin.lunar.dto.*;
import com.genshin.lunar.service.AvatarService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping("/list")
    public Result<List<AvatarList.AvatarBrief>> getList(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) List<String> elements,
                                                        @RequestParam(required = false) List<String> weaponTypes,
                                                        @RequestParam(required = false) List<String> qualityTypes) {
        return Result.ok(avatarService.getAvatarList(name, elements, weaponTypes, qualityTypes));
    }

    @GetMapping("/{id}/base-info")
    public Result<AvatarBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        AvatarBaseInfoDTO detail = avatarService.getAvatarBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "角色不存在");
        }
        return Result.ok(detail);
    }

    @GetMapping("/{id}/outfits")
    public Result<AvatarOutfitDTO> getAvatarOutfits(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarOutfits(id));
    }

    @GetMapping("/{id}/stories")
    public Result<AvatarStoryDTO> getAvatarStories(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarStories(id));
    }

    @GetMapping("/{id}/voices")
    public Result<AvatarVoiceDTO> getAvatarVoices(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarVoices(id));
    }

    @GetMapping("/{id}/constellations")
    public Result<AvatarConstellationDTO> getAvatarConstellations(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarConstellations(id));
    }

    @GetMapping("/{id}/skills")
    public Result<AvatarSkillDTO> getAvatarSkills(@PathVariable Integer id) {
        return Result.ok(avatarService.getAvatarSkills(id));
    }
}
