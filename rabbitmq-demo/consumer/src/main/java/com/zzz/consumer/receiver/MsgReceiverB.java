package com.zzz.consumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.zzz.demo.constant.MQConstant.QUEUE_B;

@Component
@RabbitListener(queues = QUEUE_B)
public class MsgReceiverB {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {
        logger.info("接收处理队列B当中的消息： " + content);
    }

}