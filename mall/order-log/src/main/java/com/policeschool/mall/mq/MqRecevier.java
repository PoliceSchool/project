package com.policeschool.mall.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "log.error", autoDelete = "false"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.queue.error.routing.key}"
        )
)
public class MqRecevier {
    /**
     * 消费消息的方法。采用消息队列监听机制
     *
     * @RabbitHandler - 代表当前方法是监听队列状态的方法，就是队列状态发生变化后，执行的消费消息的方法。
     * 方法参数。就是处理的消息的数据载体类型。
     */
    @RabbitHandler
    public void process(LogMessage msg) {
        System.out.println("Error..........receiver: " + msg);
    }
}
