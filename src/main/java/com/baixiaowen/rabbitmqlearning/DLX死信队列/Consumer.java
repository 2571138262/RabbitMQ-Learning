package com.baixiaowen.rabbitmqlearning.DLX死信队列;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        // 5、声明 虚拟主机名、路由键 这就是一个普通的交换机、队列、和路由键的配置
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";
        
        // 声明具体的交换机、队列、和绑定
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);

        
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange");
        // 这个arguments属性，要设置到声明队列上
        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 这里要进行死信队列交换机的声明、死信队列对应的queue、绑定
        channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");
        
        
        // 设置Channel， 使用自定义的Consumer，避免使用While循环来取值，
        // 这里autoAck设置为false， 手动签收
        channel.basicConsume(queueName, false, new MyConsumer(channel));
        
    }
    
}
