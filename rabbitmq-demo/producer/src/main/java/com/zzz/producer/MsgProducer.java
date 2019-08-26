package com.zzz.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.zzz.demo.constant.MQConstant.EXCHANGE_DIRECT;
import static com.zzz.demo.constant.MQConstant.EXCHANGE_FANOUT;
import static com.zzz.demo.constant.MQConstant.EXCHANGE_TOPIC;
import static com.zzz.demo.constant.MQConstant.ROUTINGKEY_A;
import static com.zzz.demo.constant.MQConstant.ROUTINGKEY_B;

@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    /**
     * 发送消息
     * @param content
     */
    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTINGKEY_A, "direct: " + content, correlationId);
        rabbitTemplate.convertAndSend(EXCHANGE_TOPIC, ROUTINGKEY_B, "topic: " + content, correlationId);
        rabbitTemplate.convertAndSend(EXCHANGE_TOPIC, "22.test_topic.22", "topic2: " + content, correlationId);
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT, null, "fanout: " + content, correlationId);
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }
}