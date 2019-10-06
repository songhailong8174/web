package com.hongyusky.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.hongyusky.web.admin.mapper.*")
@EnableTransactionManagement
@EnableRedisRepositories
@SpringBootApplication(scanBasePackages = {"com.hongyusky.web"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
