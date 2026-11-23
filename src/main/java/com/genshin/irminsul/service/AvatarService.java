package com.genshin.irminsul.service;

import com.genshin.irminsul.dto.avatar.*;
import java.util.List;

/**
 * 角色业务接口
 *
 * <p>返回的 DTO 均为只读视图对象，由实现类从 MongoDB 与 TextMap 组装
 */
public interface AvatarService {

    /**
     * 查询角色列表
     *
     * @param name         名称关键词，可选
     * @param elements     元素类型，可选
     * @param weaponTypes  武器类型，可选
     * @param qualityTypes 品质，可选
     * @return 简要角色列表，按图鉴顺序倒序
     */
    List<AvatarList.AvatarBrief> getAvatarList(
            String name, List<String> elements, List<String> weaponTypes, List<String> qualityTypes);

    /**
     * 查询角色基础信息
     *
     * @param id 角色 ID
     * @return 角色详情；不存在时返回 null
     */
    AvatarBaseInfoDTO getAvatarBaseInfo(Integer id);

    /**
     * 查询角色装扮
     *
     * @param id 角色 ID
     * @return 装扮数据
     */
    AvatarOutfitDTO getAvatarOutfits(Integer id);

    /**
     * 查询角色资料故事
     *
     * @param id 角色 ID
     * @return 故事数据
     */
    AvatarStoryDTO getAvatarStories(Integer id);

    /**
     * 查询角色语音
     *
     * @param id 角色 ID
     * @return 语音数据
     */
    AvatarVoiceDTO getAvatarVoices(Integer id);

    /**
     * 查询角色命座
     *
     * @param id 角色 ID
     * @return 命座数据；角色不存在时返回 null
     */
    AvatarConstellationDTO getAvatarConstellations(Integer id);

    /**
     * 查询角色技能
     *
     * @param id 角色 ID
     * @return 技能数据；角色不存在时返回 null
     */
    AvatarSkillDTO getAvatarSkills(Integer id);
}
