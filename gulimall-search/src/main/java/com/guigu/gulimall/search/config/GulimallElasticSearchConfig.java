package com.guigu.gulimall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Trae.Liu
 * @create: 2021-12-25 11:49
 * @description: 编写配置 注入RestHighLevelClient对象
 */
@Configuration
public class GulimallElasticSearchConfig {


    @Bean
    public RestHighLevelClient esRestClient(){
        RestClientBuilder builder = RestClient.builder(new HttpHost("114.115.149.43", 9200, "http"));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
