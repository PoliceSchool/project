package com.policeschool.mall.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitAmqpTemplate;

    //exchange 交换器名称
    @Value("${mq.config.exchange}")
    private String exchange;

    //routingkey 路由键
    @Value("${mq.config.queue.error.routing.key}")
    private String routingkey;
    /*
     * 发送消息的方法
     */
    public void send(LogMessage msg){
        /**
         * convertAndSend - 转换并发送消息的template方法。
         * 是将传入的普通java对象，转换为rabbitmq中需要的message类型对象，并发送消息到rabbitmq中。
         * 参数一：交换器名称。 类型是String
         * 参数二：路由键。 类型是String
         * 参数三：消息，是要发送的消息内容对象。类型是Object
         */
        this.rabbitAmqpTemplate.convertAndSend(this.exchange, this.routingkey, msg);
    }
}
