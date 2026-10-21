package com.genshin.lunar.controller;

import com.genshin.lunar.common.Result;
import com.genshin.lunar.dto.AvatarBaseInfoDTO;
import com.genshin.lunar.dto.AvatarList;
import com.genshin.lunar.service.AvatarService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/list")
    public Result<List<AvatarList.AvatarBrief>> getList() {
        return Result.ok(avatarService.getAvatarList());
    }

    @GetMapping("/{id}/base-info")
    public Result<AvatarBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        AvatarBaseInfoDTO detail = avatarService.getAvatarBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "角色不存在");
        }
        return Result.ok(detail);
    }

//    @GetMapping("/search")
//    public Result<List<Map<String, Object>>> searchAvatar(AvatarList query) {
//        return Result.ok(avatarService.searchAvatar(query));
//    }
//
//    @GetMapping("/simple_search")
//    public Result<List<Map<String, Object>>> searchSimple(AvatarList query) {
//        return Result.ok(avatarService.searchAvatarSimple(query));
//    }
}
