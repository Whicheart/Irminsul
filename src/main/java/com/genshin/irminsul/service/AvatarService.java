package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.*;
import java.util.List;

public interface AvatarService {
    List<AvatarList.AvatarBrief> getAvatarList(
            String name, List<String> elements, List<String> weaponTypes, List<String> qualityTypes);

    AvatarBaseInfoDTO getAvatarBaseInfo(Integer id);

    AvatarOutfitDTO getAvatarOutfits(Integer id);

    AvatarStoryDTO getAvatarStories(Integer id);

    AvatarVoiceDTO getAvatarVoices(Integer id);

    AvatarConstellationDTO getAvatarConstellations(Integer id);

    AvatarSkillDTO getAvatarSkills(Integer id);
}
