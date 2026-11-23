package com.genshin.irminsul.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.module.SimpleModule;

/** 全局 JSON 配置 */
@Configuration
public class JacksonConfig {

    /**
     * 注册全局 Double 序列化器，避免大数在 JSON 中被写成科学计数法
     *
     * @return Jackson 构建器定制器
     */
    @Bean
    public JsonMapperBuilderCustomizer plainDoubleCustomizer() {
        return builder -> builder.addModule(new SimpleModule("plain-double")
                .addSerializer(Double.class, new PlainDoubleSerializer())
                .addSerializer(Double.TYPE, new PlainDoubleSerializer()));
    }
}
