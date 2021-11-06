package com.guigu.gulimall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Trae.Liu
 * @create: 2021-11-06 14:29
 * @description: 启动类
 */
@MapperScan("com.guigu.gulimall.order.dao")
@SpringBootApplication
public class GulimallOrderApplication {

        public static void main(String[] args) {
            SpringApplication.run(GulimallOrderApplication.class, args);
        }

}
