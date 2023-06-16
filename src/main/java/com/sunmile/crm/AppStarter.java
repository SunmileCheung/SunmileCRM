package com.sunmile.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 9:26
 * @Description
 * @Modified by Sunmile
 */
@SpringBootApplication
@MapperScan("com.sunmile.crm.dao")
@EnableScheduling
public class AppStarter extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(AppStarter.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       return  builder.sources(AppStarter.class);
    }
}
