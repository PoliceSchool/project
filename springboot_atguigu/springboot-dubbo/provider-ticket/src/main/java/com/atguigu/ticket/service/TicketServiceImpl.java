package com.atguigu.ticket.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;


/**
 * 通过在application.property的文件中配置的扫描路径,可以扫描到这个文件,并且检测到
 * 这个类使用了dubbo的@Service注解,然后就会将这个类发布到application.property的文件中配置的zookeeper的地址
 */
@Component
@Service // dubbo的注解,将服务发布出去
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "倩女幽魂";
    }
}
