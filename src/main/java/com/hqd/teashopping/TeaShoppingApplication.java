package com.hqd.teashopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 潮茶商城系统启动类
 */
@SpringBootApplication
@MapperScan("com.hqd.teashopping.mapper") // 扫描所有 Mapper 接口
public class TeaShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeaShoppingApplication.class, args);
    }

}