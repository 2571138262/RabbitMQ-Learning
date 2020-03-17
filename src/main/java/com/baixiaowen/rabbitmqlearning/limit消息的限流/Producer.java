package com.baixiaowen.rabbitmqlearning.limit消息的限流;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";
        
        
        // 6、发送消息
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ QOS Message";
            channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());   
        }
        
    }
    
}
