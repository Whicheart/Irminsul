package com.genshin.irminsul.controller;

import com.genshin.irminsul.common.Result;
import com.genshin.irminsul.dto.ReliquaryBaseInfoDTO;
import com.genshin.irminsul.dto.ReliquaryList;
import com.genshin.irminsul.dto.ReliquaryPropRankDTO;
import com.genshin.irminsul.service.ReliquaryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reliquary")
public class ReliquaryController {
    private final ReliquaryService reliquaryService;

    @GetMapping("/list")
    public Result<List<ReliquaryList>> getList() {
        return Result.ok(reliquaryService.getReliquaryList());
    }

    @GetMapping("/prop-rank")
    public Result<ReliquaryPropRankDTO> getPropRank() {
        return Result.ok(reliquaryService.getReliquaryPropRank());
    }

    @GetMapping("/{id}/base-info")
    public Result<ReliquaryBaseInfoDTO> getBaseInfo(@PathVariable Integer id) {
        ReliquaryBaseInfoDTO detail = reliquaryService.getReliquaryBaseInfo(id);
        if (detail == null) {
            return Result.error(404, "圣遗物套装不存在");
        }
        return Result.ok(detail);
    }
}
