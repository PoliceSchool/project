package com.atguigu.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * SpringBoot默认支持两种技术来和ES交互
 * 1、Jest(默认不生效), 需要导入jest的工具包(io.searchbox.client.JestClient)
 * 2、SpringData ElasticSearch
 * 1) client 节点信息clusterNodes;clusterName
 * 2) ElasticsearchTemplate 操作es
 * 3) 编写一个ElasticsearchRepository的子接口来操作es
 */
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.atguigu.elastic.repository")
public class SpringbootElasticApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootElasticApplication.class, args);
    }

}
