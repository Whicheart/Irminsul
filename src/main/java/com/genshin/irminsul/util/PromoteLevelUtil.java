package com.genshin.irminsul.util;

/**
 * 等级与突破等级的换算工具
 */
public final class PromoteLevelUtil {

    private PromoteLevelUtil() {}

    /**
     * 按等级阈值换算突破等级：20/40/50/60/70/80 级依次为 1~6
     */
    public static int toPromoteLevel(int level) {
        if (level >= 80) return 6;
        if (level >= 70) return 5;
        if (level >= 60) return 4;
        if (level >= 50) return 3;
        if (level >= 40) return 2;
        if (level >= 20) return 1;
        return 0;
    }
}
