package com.genshin.irminsul.dto.avatar;

import java.util.List;
import lombok.Data;

/** 角色技能数据 */
@Data
public class AvatarSkillDTO {

    // 角色 ID
    private Integer id;

    // 主动技能
    private List<CombatSkill> combatSkills;
    // 固有天赋
    private List<PassiveSkill> passiveSkills;

    // 特殊天赋（老角色加强）
    private List<SpecialProudSkill> specialProudSkills;

    // 主动技能
    @Data
    public static class CombatSkill {

        // 技能 ID
        private Integer id;

        // 名称
        private String name;
        // 描述
        private String desc;
        // 技能旁白
        private String extraDesc;
        // 加强描述
        private String descExtra;
        // 补充描述
        private String loreDesc;

        // 图标名
        private String iconName;

        // 各等级倍率
        private List<CombatSkillData> combatSkillDataList;
    }

    // 固有天赋
    @Data
    public static class PassiveSkill {

        // 技能 ID
        private Integer id;

        // 名称
        private String name;
        // 描述
        private String desc;
        // 技能旁白
        private String extraDesc;
        // 加强描述
        private String descExtra;
        // 补充描述
        private String loreDesc;

        // 图标名
        private String iconName;
    }

    // 特殊天赋
    @Data
    public static class SpecialProudSkill {

        // 技能 ID
        private Integer id;

        // 基础信息
        // 名称
        private String name;
        // 描述
        private String desc;
        // 技能旁白
        private String extraDesc;
        // 加强描述
        private String descExtra;
        // 补充描述
        private String loreDesc;

        // 图像资源
        // 图标名
        private String iconName;

        // 技能倍率
        // 各等级倍率
        private List<CombatSkillData> combatSkillDataList;
    }

    // 单级技能倍率
    @Data
    public static class CombatSkillData {

        // 等级
        private Integer level;
        // 倍率参数描述
        private List<String> paramDescList;
        // 倍率数值
        private List<Double> paramList;
    }
}
