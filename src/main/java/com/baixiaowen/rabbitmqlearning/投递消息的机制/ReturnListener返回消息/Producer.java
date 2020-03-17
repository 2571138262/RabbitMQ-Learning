package com.baixiaowen.rabbitmqlearning.投递消息的机制.ReturnListener返回消息;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
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
        String routingKey = "return.save";
        String routingKeyError = "abc.save";
        
        // 添加ReturnListener   之前之后都无所谓，因为是异步操作
        channel.addReturnListener(new ReturnListener() {
            /**
             * 
             * @param replyCode 响应码
             * @param replyText 响应文本
             * @param exchange  交换机
             * @param routingKey    路由键
             * @param properties    消息属性
             * @param body  消息体
             * @throws IOException
             */
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("------ handler return ------ ");
                System.out.println("replyCode: " + replyCode);
                System.out.println("replyText: " + replyText);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                System.out.println("properties: " + properties);
                System.out.println("body: " + new String(body));
            }
        });
        
        // 6、发送消息
        String msg = "Hello RabbitMQ Return Message";
//        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());

        channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());
        
    }
    
}
