package com.baixiaowen.rabbitmqlearning.Consumer自定义消费者;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 2、配置ConnectionFactory参数
        factory.setHost("183.2.169.17");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        
        // 3、创建Connection 
        Connection connection = factory.newConnection();
        
        // 4、创建Channel
        Channel channel = connection.createChannel();
        
        // 5、声明 虚拟主机名、路由键
        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";
        
        
        // 6、发送消息
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ Return Message";
            channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());   
        }
        
    }
    
}
