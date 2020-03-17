package com.baixiaowen.rabbitmqlearning.投递消息的机制.confirmListener确认模式;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
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
        
        // 5、指定消息投递模式：消息的确认模式
        channel.confirmSelect();
        
        // 6、声明属性 交换机名、路由键
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";
        
        // 7、发送一条消息
        String msg = "Hello RabbitMQ Send confirm message";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
        
        // 8、添加一个确认监听，就是MQ Broker在确认收到消息之后会返回给消息投递方一个Ack信号，这里负责监听这个信号
        // 这是异步操作
        channel.addConfirmListener(new ConfirmListener() {
            /**
             * 
             * @param deliveryTag 这条消息的唯一的标签
             * @param multiple    
             * @throws IOException
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("-------- Ack --------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("-------- no Ack --------"); 
            }
        });
        
        
    }
    
}
