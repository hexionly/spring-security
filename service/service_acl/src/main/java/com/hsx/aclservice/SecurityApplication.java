package com.hsx.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author HEXIONLY
 * @date 2022/3/7 16:38
 */
@SpringBootApplication
@MapperScan(basePackages = "com.hsx.aclservice.mapper")
@ComponentScan(basePackages = "com.hsx")
@EnableDiscoveryClient
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
