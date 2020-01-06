package com.policeschool.mall.controller;

import com.policeschool.mall.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/6.
 */
@RestController
@RequestMapping("/product")
public class TestController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/save-redis")
    public void testRedis(String key, String value) {
        System.out.println("key:" + key + ", " + "value:" + value);
        redisService.set(key, value);
    }
}
