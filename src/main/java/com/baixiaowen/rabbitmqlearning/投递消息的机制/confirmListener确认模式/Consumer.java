package com.baixiaowen.rabbitmqlearning.投递消息的机制.confirmListener确认模式;

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
        // 2、配置ConnectionFactory的属性, 地址、端口、虚拟主机
        factory.setHost("183.2.169.17");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        // 3、创建Connection
        Connection connection = factory.newConnection();

        // 4、通过Connection去创建Channel， 获取连接信道
        Channel channel = connection.createChannel();
        
        // 5、声明 交换机名、路由键
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";
        String queueName = "test_confirm_queue";
        
        // 这三步可以放到Consumer也可以放到Producer
        // 6、声明具体的Exchange交换机
        // 7、创建队列
        // 8、绑定Exchange和Queue，指定路由Key
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        
        // 9、创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        
        // 10、设置消费者的消费模式
        channel.basicConsume(queueName, true, consumer);
        
        // 11、循环读取数据
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端接收到：" + msg);
        }
        
    }
    
}
