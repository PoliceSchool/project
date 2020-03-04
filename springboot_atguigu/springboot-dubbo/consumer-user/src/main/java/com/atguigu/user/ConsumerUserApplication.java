package com.atguigu.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 将服务提供者注册到注册中心
 * 1. 引入dubbo依赖和zokeeper依赖
 * 2. 配置注册中心地址
 * 3. 使用dubbo的@Service注解发布服务
 */
@SpringBootApplication
public class ConsumerUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerUserApplication.class, args);
	}

}
