package com.zzz.consumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.zzz.demo.MQConstant.QUEUE_C;

@Component
public class MsgReceiverC {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    @RabbitListener(queues = QUEUE_C)
    public void process(String content) {
        logger.info("接收处理队列C当中的消息： " + content);
    }


    @RabbitHandler
    @RabbitListener(queues = QUEUE_C)
    public void process1(String content) {
        logger.info("接收处理队列C1当中的消息： " + content);
    }

    @RabbitHandler
    @RabbitListener()
    public void processAnon(String content) {
        logger.info("接收处理队列C2当中的消息： " + content);
    }
}