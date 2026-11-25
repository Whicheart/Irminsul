package com.genshin.irminsul.util;

/**
 * 战斗属性枚举与中文名称的转换工具
 */
public final class FightPropUtil {

    private FightPropUtil() {}

    /**
     * 把属性枚举转换为中文属性名，未收录的返回"未知"
     */
    public static String translateStat(String stat) {
        if (stat == null) {
            return "未知";
        }
        return switch (stat) {
            case "FIGHT_PROP_HP_PERCENT" -> "生命值";
            case "FIGHT_PROP_ATTACK_PERCENT" -> "攻击力";
            case "FIGHT_PROP_DEFENSE_PERCENT" -> "防御力";
            case "FIGHT_PROP_ELEMENT_MASTERY" -> "元素精通";
            case "FIGHT_PROP_CHARGE_EFFICIENCY" -> "元素充能效率";
            case "FIGHT_PROP_CRITICAL" -> "暴击率";
            case "FIGHT_PROP_CRITICAL_HURT" -> "暴击伤害";
            case "FIGHT_PROP_HEAL_ADD" -> "治疗加成";
            case "FIGHT_PROP_FIRE_ADD_HURT" -> "火元素伤害加成";
            case "FIGHT_PROP_WATER_ADD_HURT" -> "水元素伤害加成";
            case "FIGHT_PROP_ICE_ADD_HURT" -> "冰元素伤害加成";
            case "FIGHT_PROP_ELEC_ADD_HURT" -> "雷元素伤害加成";
            case "FIGHT_PROP_WIND_ADD_HURT" -> "风元素伤害加成";
            case "FIGHT_PROP_ROCK_ADD_HURT" -> "岩元素伤害加成";
            case "FIGHT_PROP_GRASS_ADD_HURT" -> "草元素伤害加成";
            case "FIGHT_PROP_PHYSICAL_ADD_HURT" -> "物理伤害加成";
            default -> stat;
        };
    }
}
