package com.genshin.lunar.service;

import com.genshin.lunar.entity.Avatar;
import com.genshin.lunar.entity.AvatarQuery;

import java.util.List;
import java.util.Map;

public interface AvatarService {
    List<Map<String, Object>> searchAvatar(AvatarQuery query);
    List<Map<String, Object>> searchAvatarSimple(AvatarQuery query);
}
