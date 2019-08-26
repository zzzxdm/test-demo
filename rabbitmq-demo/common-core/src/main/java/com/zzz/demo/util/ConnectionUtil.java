package com.zzz.demo.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zzz.demo.constant.ProducerConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
public class ConnectionUtil {

    public static Connection getConnection() {
        //New一个RabbitMQ的连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置需要连接的RabbitMQ地址，这里指向本机
        factory.setHost(ProducerConstant.HOST);
        factory.setPort(ProducerConstant.PORT);
        factory.setVirtualHost(ProducerConstant.VHOST);
        factory.setUsername(ProducerConstant.USER_NAME);
        factory.setPassword(ProducerConstant.PASSWORD);
        try {
            return factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

}
