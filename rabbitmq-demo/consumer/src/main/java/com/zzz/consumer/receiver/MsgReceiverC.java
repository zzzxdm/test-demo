package com.zzz.consumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.zzz.demo.constant.MQConstant.QUEUE_C;
import static com.zzz.demo.constant.MQConstant.QUEUE_ONE;
import static com.zzz.demo.constant.MQConstant.QUEUE_TWO;

@Component
public class MsgReceiverC {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = QUEUE_C)
    public void process(String content) {
        logger.info("接收处理队列C当中的消息： " + content);
    }


    @RabbitListener(queues = QUEUE_C)
    public void process1(String content) {
        logger.info("接收处理队列C1当中的消息： " + content);
    }


    @RabbitListener(queues = QUEUE_ONE)
    public void processX1(String content) {
        logger.info("接收处理队列QUEUE_ONE当中的消息： " + content);
    }

    @RabbitListener(queues = QUEUE_TWO)
    public void processX2(String content) {
        logger.info("接收处理队列QUEUE_TWO当中的消息： " + content);
    }
}