package com.github.fid3lles.imobiliaria_core.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${app.media.base-dir}")
    private String baseDir;

    @Value("${app.media.base-url:/propriedades}")
    private String baseUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ex: /propriedades/** -> file:/data/propriedades/**
        registry.addResourceHandler(baseUrl + "/**")
                .addResourceLocations("file:" + baseDir + "/");
    }
}
