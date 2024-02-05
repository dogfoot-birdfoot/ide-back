package com.ide.back.config;

import com.ide.back.config.interceptor.ApiInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.inject.Inject;

@Configuration
public class EndpointConfig implements WebMvcConfigurer {

    @Inject
    ApiInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);

        registry.addInterceptor(this.apiInterceptor).addPathPatterns("/api/**");
    }
}
