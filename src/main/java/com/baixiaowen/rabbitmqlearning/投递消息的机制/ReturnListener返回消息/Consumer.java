package com.baixiaowen.rabbitmqlearning.投递消息的机制.ReturnListener返回消息;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

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
        String exchangeName = "test_return_exchange";
        String routingKey = "return.#";
        String queueName = "test_return_queue";
        
        // 声明具体的交换机、队列、和绑定
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);

        // 设置Channel
        channel.basicConsume(queueName, true, consumer);
        
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费者受到：" + msg);
        }
        
    }
    
}
