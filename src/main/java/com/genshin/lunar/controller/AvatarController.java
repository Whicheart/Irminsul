package com.genshin.lunar.controller;

import com.genshin.lunar.common.Result;
import com.genshin.lunar.dto.AvatarQuery;
import com.genshin.lunar.service.AvatarService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/search")
    public Result<List<Map<String, Object>>> searchAvatar(AvatarQuery query) {
        return Result.ok(avatarService.searchAvatar(query));
    }

    @GetMapping("/simple_search")
    public Result<List<Map<String, Object>>> searchSimple(AvatarQuery query) {
        return Result.ok(avatarService.searchAvatarSimple(query));
    }
}
