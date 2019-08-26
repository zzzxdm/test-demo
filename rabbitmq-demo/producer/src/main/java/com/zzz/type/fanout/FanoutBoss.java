package com.zzz.type.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

import static com.zzz.demo.util.ConnectionUtil.getConnection;

@Slf4j
public class FanoutBoss {

    public static void main(String[] args) {
        // 发送消息到匿名队列
        sendMsg2AnonQueue();
    }

    public static void sendMsg2AnonQueue() {
        try {
            //尝试获取一个连接
            Connection connection = getConnection();
            //尝试创建一个channel
            Channel channel = connection.createChannel();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTime = dtf.format(LocalDateTime.now());
            String message = "当前时间为:" + currentTime;
            //声明交换机（参数为：交换机名称; 交换机类型，广播模式）
            channel.exchangeDeclare("fanoutLogs", BuiltinExchangeType.FANOUT);
            //消息发布（参数为：交换机名称; routingKey，忽略。在广播模式中，生产者声明交换机的名称和类型即可）
            channel.basicPublish("fanoutLogs", "", null, message.getBytes());
            log.info("********Message********:发送成功");
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
