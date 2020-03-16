package com.baixiaowen.rabbitmqlearning.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
        
        // 4、声明（创建）一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);
        
        // 5、创建消费者  建立在当前channel的Consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        
        // 6、设置Channel
        /**
         * autoAck 是否自动签收
         *  Broker 和 消费端Consumer 进行监听数据消费的过程中，假设Broker有一条消息发到了Consumer中，
         *  Consumer马上就会回送给Broker一条Ack消息，告诉它这条消息Consumer收到了
         *  如果设置autoAck为true，它会自动帮我们响应Ack消息
         *  也可以设置手动的响应Ack，但是需要在代码中使用
         */
        channel.basicConsume(queueName, true, queueingConsumer);
        
        while (true) {
            // 7、获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端" + msg);
        }
    }
    
}
