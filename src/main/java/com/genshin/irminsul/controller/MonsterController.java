package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.monster.MonsterBaseInfoDTO;
import com.genshin.irminsul.dto.monster.MonsterList;
import com.genshin.irminsul.service.MonsterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 敌对生物接口
 *
 * <p>列表按图鉴顺序返回，可按名称与子类型筛选；详情接口在生物不存在时返回 404
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monster")
public class MonsterController {

    // 敌对生物业务逻辑，由构造器注入
    private final MonsterService monsterService;

    /**
     * 敌对生物列表
     *
     * @param name  名称关键词，可选，仅做包含匹配
     * @param types 图鉴子类型，可选；为空返回全部
     * @return 简要生物列表
     */
    @GetMapping("/list")
    public Result<List<MonsterList.MonsterBrief>> getList(
            @RequestParam(required = false) String name, @RequestParam(required = false) List<String> types) {
        return Result.ok(monsterService.getMonsterList(name, types));
    }

    /**
     * 敌对生物基础信息，包含三维成长与元素抗性
     *
     * @param id 生物 ID
     * @return 生物详情；不存在时返回 404 错误响应
     */
    @GetMapping("/{id}/base-info")
    public Result<MonsterBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        MonsterBaseInfoDTO detail = monsterService.getMonsterBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "敌对生物不存在");
        }
        return Result.ok(detail);
    }
}
