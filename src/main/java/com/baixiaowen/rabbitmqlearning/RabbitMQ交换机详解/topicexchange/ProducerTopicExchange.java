package com.baixiaowen.rabbitmqlearning.RabbitMQ交换机详解.topicexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerTopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 配置ConnectionFactory 地址、端口，虚拟主机
        factory.setHost("183.2.169.17");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        
        // 2、创建Connection
        Connection connection = factory.newConnection();
        
        // 3、创建Channel
        Channel channel = connection.createChannel();
        
        // 4、声明
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";
        
        // 5、发送
        String msg = "Hello World RabbitMQ 4 Topic Exchange Message ...";
        channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());
        
        channel.close();
        connection.close();
    }
    
}
