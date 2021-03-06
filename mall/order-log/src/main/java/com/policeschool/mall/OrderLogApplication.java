package com.policeschool.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderLogApplication.class, args);
    }

}
