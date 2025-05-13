package com.chengfu.usercenterapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan("com.chengfu.usercenterapi.mapper")
public class UserCenterAPIApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterAPIApplication.class, args);
    }

}
