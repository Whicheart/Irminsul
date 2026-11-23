package com.genshin.irminsul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动入口
 *
 * <p>组件扫描根为 {@code com.genshin.irminsul}，启动时初始化 MongoDB 仓储、
 * Caffeine 本地缓存与全局 JSON 序列化配置
 */
@SpringBootApplication
public class IrminsulApplication {

    /**
     * 启动 Spring 容器
     *
     * @param args 命令行参数，通常为空
     */
    public static void main(String[] args) {
        SpringApplication.run(IrminsulApplication.class, args);
    }
}
