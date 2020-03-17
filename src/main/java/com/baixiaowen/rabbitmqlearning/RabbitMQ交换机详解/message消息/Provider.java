package com.baixiaowen.rabbitmqlearning.RabbitMQ交换机详解.message消息;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        Map<String, Object> handers = new HashMap<>();
        handers.put("my1", 111);
        handers.put("my2", 222);
        
        
        // 创建Message
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)     //常用的模式，2为持久化的投递， 1为不是持久化投递（服务重启就没了） 
                .contentEncoding("UTF-8")   // 字符集
                .expiration("10000")        // 设置过期时间， 对应的是消息的，如果在管控台中设置Queue的有效时间，那个时间是对整个queue的
                .headers(handers)           // 添加自定义属性        
                .build();
        
        // 4、通过Channel发送数据
        for (int i = 0; i < 5; i++) {
            String message = "Hello RabbitMQ!";
            // 1 exchange   2 routingKey 
            channel.basicPublish("", "test001", properties, message.getBytes());
        }

        // 5、关闭相关的连接 （由小到大的关闭）
        channel.close();
        connection.close();
    }
    
}
