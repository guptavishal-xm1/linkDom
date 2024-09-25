package com.linkdom.config;

import com.linkdom.MyCustomInterceptor.intercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Autowired
    intercept MyIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(MyIntercept)
                .addPathPatterns("/profile")
                .addPathPatterns("/dashboard")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register");
    }
}
