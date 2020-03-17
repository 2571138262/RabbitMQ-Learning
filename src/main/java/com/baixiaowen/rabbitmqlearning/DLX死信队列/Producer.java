package com.baixiaowen.rabbitmqlearning.DLX死信队列;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";
        
        
        // 6、发送消息
        for (int i = 0; i < 1; i++) {
            Map<String, Object> handers = new HashMap<>();
            handers.put("num", i);

            // 添加额外的属性
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)    //常用的模式，2为持久化的投递， 1为不是持久化投递（服务重启就没了） 
                    .contentEncoding("UTF-8")   // 字符集
                    .expiration("10000")        // 设置过期时间
                    .headers(handers)           // 添加自定义属性        
                    .build();
            
            String msg = "Hello RabbitMQ DLX Message    " + i; 
            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());   
        }
        
    }
    
}
