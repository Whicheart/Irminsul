package com.genshin.lunar.mapper;

import com.genshin.lunar.entity.Banner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {
    List<Banner> getAllBanners(Banner banner);
}
