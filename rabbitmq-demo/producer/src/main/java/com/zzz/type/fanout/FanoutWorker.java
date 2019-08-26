package com.zzz.type.fanout;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.zzz.demo.util.ConnectionUtil.getConnection;

@Slf4j
public class FanoutWorker {

    public static void main(String[] args) {
        int threadCount = 5;
        try {
            Connection connection = getConnection();
            Channel channel = connection.createChannel();
            for (int i = 0; i < threadCount; i++) {
                new Thread(() -> receiveMsg(channel), "thread" + i).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void receiveMsg(Channel channel) {
        try {
            //获取一个临时队列
            String queueName = channel.queueDeclare().getQueue();
            //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
            channel.queueBind(queueName, "fanoutLogs", "");
            //这里重写了DefaultConsumer的handleDelivery方法，因为发送的时候对消息进行了getByte()，在这里要重新组装成String
            String name = Thread.currentThread().getName();
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String message = new String(body, "UTF-8");
                    log.info(name + " received:" + message);
                }
            };

            //声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
