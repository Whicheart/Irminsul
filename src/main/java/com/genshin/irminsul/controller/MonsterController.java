package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.monster.MonsterList;
import com.genshin.irminsul.service.MonsterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monster")
public class MonsterController {

    private final MonsterService monsterService;

    @GetMapping("/list")
    public Result<List<MonsterList.MonsterBrief>> getList(
            @RequestParam(required = false) String name, @RequestParam(required = false) List<String> types) {
        return Result.ok(monsterService.getMonsterList(name, types));
    }
}
