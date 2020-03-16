package com.baixiaowen.rabbitmqlearning.RabbitMQ交换机详解.fanoutexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerFanoutExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 2、配置Connection 地址、端口、虚拟主机
        factory.setHost("183.2.169.17");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        
        // 3、创建Connection
        Connection connection = factory.newConnection();

        // 4、创建Channel
        Channel channel = connection.createChannel();
        
        // 5、声明
        String exchangeName = "test_fanout_exchange";
        
        // 6、发送
        for (int i = 0; i < 10; i++) {
            String mes = "Hello World RabbitMQ 4 Fanout Exchange Message ...";
            channel.basicPublish(exchangeName, "n,nmn,mn", null, mes.getBytes());
        }
        
        channel.close();
        connection.close();
    }
    
}
