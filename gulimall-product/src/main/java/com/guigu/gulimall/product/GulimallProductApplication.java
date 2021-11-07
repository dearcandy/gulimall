package com.guigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: Trae.Liu
 * @create: 2021-11-06 14:29
 * @description: 启动类
 */
@EnableDiscoveryClient
@MapperScan("com.guigu.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

        public static void main(String[] args) {
            SpringApplication.run(GulimallProductApplication.class, args);
        }

}
