package com.zzz.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 测试kafka生产者
 */
@RestController
public class ProducerController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;


    // 发送消息
    @GetMapping("/normal/{message}")
    public void sendMessage1(@PathVariable("message") String normalMessage) {
        kafkaTemplate.send("topic1", normalMessage);
    }

    @GetMapping("/callback1/{message}")
    public void sendMessage2(@PathVariable("message") String callbackMessage) {
        kafkaTemplate.send("topic1", callbackMessage).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, failure -> System.out.println("发送消息失败:" + failure.getMessage()));
    }

    @GetMapping("/callback2/{message}")
    public void sendMessage3(@PathVariable("message") String callbackMessage) {
        kafkaTemplate.send("topic1", callbackMessage).addCallback(
                new ListenableFutureCallback<SendResult<String, String>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        System.out.println("发送消息失败：" + ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                                + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
                    }
                });
    }


}
