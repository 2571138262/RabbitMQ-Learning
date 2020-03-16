package com.baixiaowen.rabbitmqlearning.RabbitMQ交换机详解.topicexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1、创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 配置连接工厂 地址、端口、虚拟主机、超时自动连接
        factory.setHost("183.2.169.17");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);
        
        // 2、常见Connection
        Connection connection = factory.newConnection();
        // 3、创建Channel
        Channel channel = connection.createChannel();
        
        // 4、声明
        String exchangeName = "test_topic_exchange";
        String exchangeType = "topic";
        String queueName = "test_topic_queue";
        String routingKey = "user.*";
        
        // 5、声明交换机 名称、类型、是否持久化。。。
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        
        // 6、声明一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        
        // 7、建立交换机和队列的绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);
        
        // 8、durable 是否持久化消息 创建消费者  建立在当前channel的Consumer
        QueueingConsumer consumer = new QueueingConsumer(channel);

        // 参数：队列名称，是否自动ACK签收，Consumer 
        channel.basicConsume(queueName, true, consumer);

        // 循环获取消息
        while (true){
            // 获取信息，如果没有消息，这一步会一直阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息 ：" + msg);
        }

    }
    
}
