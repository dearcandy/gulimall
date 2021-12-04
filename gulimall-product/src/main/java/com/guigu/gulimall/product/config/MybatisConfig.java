package com.guigu.gulimall.product.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: Trae.Liu
 * @create: 2021-12-05 00:00
 * @description:
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.guigu.gulimall.product.dao")
public class MybatisConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
      PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
      paginationInterceptor.setOverflow(true);
      paginationInterceptor.setLimit(1000);
      return paginationInterceptor;
    }


}
