package com.sunmile.crm.config;

import com.sunmile.crm.interceptor.CheckLoginInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 10:38
 * @Description
 * @Modified by Sunmile
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public CheckLoginInterceptor getCheckLoginInterceptor(){
        return new CheckLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getCheckLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index","/css/**","/images/**","/js/**","/lib/**","/user/login");

    }
}
