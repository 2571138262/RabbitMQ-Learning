package com.baixiaowen.rabbitmqlearning.RabbitMQ交换机详解.directexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerDirectExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建CollectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 配置连接工厂 : 连接地址、端口、虚拟主机
        connectionFactory.setHost("183.2.169.17");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        
        // 2、创建Connection
        Connection connection = connectionFactory.newConnection();
        // 3、创建Channel
        Channel channel = connection.createChannel();
        // 4、声明 交换机名称   路由key
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";
        // 5、发送
        String msg = "Hello world RabbitMQ 4 Direct Exchange Message ...";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        channel.close();
        connection.close();
        
    }
    
}
