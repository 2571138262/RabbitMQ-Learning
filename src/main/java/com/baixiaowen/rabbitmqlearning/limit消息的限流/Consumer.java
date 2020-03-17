package com.baixiaowen.rabbitmqlearning.limit消息的限流;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
        String routingKey = "qos.#";
        String queueName = "test_gos_queue";
        
        // 声明具体的交换机、队列、和绑定
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        
        // 1、限流方式  第一件事就是 autoAck 设置为 false
        channel.basicQos(0, 1, false);
        

        
        // 设置Channel， 使用自定义的Consumer，避免使用While循环来取值，
        // 这里autoAck设置为false， 手动签收
        channel.basicConsume(queueName, false, new MyConsumer(channel));
        
    }
    
}
