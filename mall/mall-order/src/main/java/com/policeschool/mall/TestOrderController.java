package com.policeschool.mall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: 测试类
 * date: Created in 2020/1/3.
 *
 * @author lujingxiao
 */
@RestController
@RequestMapping("/order")
public class TestOrderController {
    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/index")
    public String index() {
        return "您访问的是：【"+serviceName+"】服务,【端口号】"+port;
    }
}
