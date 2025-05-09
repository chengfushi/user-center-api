package com.chengfu.usercenterapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chengfu.usercenterapi.mapper")
public class UserCenterAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterAPIApplication.class, args);
	}

}
