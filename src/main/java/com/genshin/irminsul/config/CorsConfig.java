package com.genshin.irminsul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 本地开发跨域配置
 *
 * <p>仅放行 Vite 与 CRA 的默认端口；生产环境应通过网关或反向代理同源部署，
 * 不要沿用这里的宽泛规则
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * 注册全局 CORS 规则
     *
     * @param registry CORS 注册表，由 Spring MVC 注入
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5173", "http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
