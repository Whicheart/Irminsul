package com.genshin.lunar.controller;

import com.genshin.lunar.entity.Banner;
import com.genshin.lunar.service.BannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping("/all")
    public List<Banner> getAllBanners(Banner banner){
        return bannerService.getAllBanners(banner);
    }
}
