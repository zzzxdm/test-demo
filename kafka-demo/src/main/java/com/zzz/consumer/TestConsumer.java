package com.zzz.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * kafka消费者测试
 */
@Component
public class TestConsumer {


    @KafkaListener(groupId = "defaultGroup", topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = {"0"})
    })
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
    }

    // 消费监听
    @KafkaListener(topics = {"topic1"})
    public void onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }

}
