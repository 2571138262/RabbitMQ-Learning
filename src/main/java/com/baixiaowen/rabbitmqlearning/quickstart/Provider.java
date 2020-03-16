package com.baixiaowen.rabbitmqlearning.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建连接工厂 ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 配置连接工厂 : 连接地址、端口、虚拟主机
        connectionFactory.setHost("183.2.169.17");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 2、通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3、通过Connection创建一个Channel
        Channel channel = connection.createChannel();

        // 4、通过Channel发送数据
        for (int i = 0; i < 5; i++) {
            String message = "Hello RabbitMQ!";
            // 1 exchange   2 routingKey 
            channel.basicPublish("", "test001", null, message.getBytes());
        }

        // 5、关闭相关的连接 （由小到大的关闭）
        channel.close();
        connection.close();
    }
    
}
