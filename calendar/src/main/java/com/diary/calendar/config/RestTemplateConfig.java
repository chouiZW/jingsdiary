package com.diary.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * 配置RestTemplate实例，供微信登录接口调用微信官方API
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}