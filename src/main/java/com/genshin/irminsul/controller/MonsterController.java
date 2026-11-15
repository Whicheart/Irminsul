package com.genshin.irminsul.controller;

import com.genshin.irminsul.service.MonsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monster")
public class MonsterController {
    private final MonsterService monsterService;
}
