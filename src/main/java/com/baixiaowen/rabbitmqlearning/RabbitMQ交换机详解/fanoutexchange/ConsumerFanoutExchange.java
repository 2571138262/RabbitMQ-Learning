package com.baixiaowen.rabbitmqlearning.RabbitMQ交换机详解.fanoutexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerFanoutExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1、创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 2、配置Connection 地址、端口、虚拟主机、 重试时间
        factory.setHost("183.2.169.17");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);
        
        // 3、创建Connection
        Connection connection = factory.newConnection();
        
        // 4、创建Channel
        Channel channel = connection.createChannel();
        
        // 5、声明 交换机名、交换机类型、路由键、队列名称
        String exchangeName = "test_fanout_exchange";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String routingKey = ""; // 不设置路由键
        
        // 6、声明交换机名、类型、是否持久化
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        
        // 7、声明一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        
        // 8、建立交换机和队列的绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);
        
        // 创建消费者，建立当前Channel的Consumer
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 参数：队列名称，是否自动ACK签收，Consumer 
        channel.basicConsume(queueName, true, consumer);
        // 循环获取消息
        while (true) {
            // 获取消息，如果没有消息，这一步将会一直阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息" + msg);
        }
        
        
    }
    
}
