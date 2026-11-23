package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.reliquary.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.reliquary.ReliquaryPropRankDTO;
import com.genshin.irminsul.dto.reliquary.ReliquarySetList;
import com.genshin.irminsul.service.ReliquaryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 圣遗物接口
 *
 * <p>提供套装列表、套装详情与主副词条档位；套装不存在时详情接口返回 404
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reliquary")
public class ReliquaryController {

    // 圣遗物业务逻辑，由构造器注入
    private final ReliquaryService reliquaryService;

    /**
     * 圣遗物套装列表
     *
     * @param name  套装名称关键词，可选，仅做包含匹配
     * @param ranks 星级筛选，可选
     * @return 套装简要列表
     */
    @GetMapping("/list")
    public Result<List<ReliquarySetList.RelicSetBrief>> getList(
            @RequestParam(required = false) String name, @RequestParam(required = false) List<Integer> ranks) {
        return Result.ok(reliquaryService.getReliquaryList(name, ranks));
    }

    /**
     * 各星级圣遗物的主属性与副词条档位
     *
     * @return 按星级分组的词条档位
     */
    @GetMapping("/prop-rank")
    public Result<ReliquaryPropRankDTO> getPropRank() {
        return Result.ok(reliquaryService.getReliquaryPropRank());
    }

    /**
     * 圣遗物套装详情，包含套装效果、部件与故事
     *
     * @param id 套装 ID
     * @return 套装详情；不存在时返回 404 错误响应
     */
    @GetMapping("/{id}/base-info")
    public Result<ReliquaryBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        ReliquaryBaseInfoDTO detail = reliquaryService.getReliquaryBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "圣遗物套装不存在");
        }
        return Result.ok(detail);
    }
}
