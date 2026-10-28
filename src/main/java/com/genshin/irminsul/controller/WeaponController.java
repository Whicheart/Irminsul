package com.genshin.irminsul.controller;

import com.genshin.irminsul.service.WeaponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weapon")
public class WeaponController {
    private final WeaponService weaponService;
}
