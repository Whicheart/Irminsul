package com.genshin.lunar.service.impl;

import com.genshin.lunar.entity.Banner;
import com.genshin.lunar.mapper.BannerMapper;
import com.genshin.lunar.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    private final BannerMapper bannerMapper;

    public BannerServiceImpl(BannerMapper bannerMapper) {
        this.bannerMapper = bannerMapper;
    }

    @Override
    public List<Banner> getAllBanners(Banner banner){
        return bannerMapper.getAllBanners(banner);
    }
}
