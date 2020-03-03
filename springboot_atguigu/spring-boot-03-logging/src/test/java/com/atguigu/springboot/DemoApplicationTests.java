package com.atguigu.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
// 这里的类的权限必须为public,不然会报错,并且使用maven package的时候也不会通过
public class DemoApplicationTests {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() {
        // 日志的级别
        // 由低到高: trace<debug<info<warn<error
        // 可以调整输出的日志级别;日志就只会在这个级别以后的高级别生效
        logger.trace("这是trace日志....");
        logger.debug("这是debug日志....");
        // SpringBoot默认给我们使用的是info级别,没有知道级别的就用SpringBoot默认规定的级别
        logger.info("这是info日志....");
        logger.warn("这是warning日志....");
        logger.error("这是error日志....");
    }
}
