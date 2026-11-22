package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.monster.MonsterBaseInfoDTO;
import com.genshin.irminsul.dto.monster.MonsterList;
import com.genshin.irminsul.service.MonsterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/base-info")
    public Result<MonsterBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        MonsterBaseInfoDTO detail = monsterService.getMonsterBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "敌对生物不存在");
        }
        return Result.ok(detail);
    }
}
