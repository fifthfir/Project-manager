package com.example.springboot.config;

import com.example.springboot.config.intercepter.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/user/login", "/user/register", "/file/**", "/menu/**", "/menu", "/comment/**");
    }
}
