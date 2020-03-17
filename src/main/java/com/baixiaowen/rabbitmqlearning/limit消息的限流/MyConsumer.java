package com.baixiaowen.rabbitmqlearning.limit消息的限流;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * 自定义Consumer， 用于MQ数据的消费
 */
public class MyConsumer extends DefaultConsumer {
    
    private Channel channel;
    
    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//        super.handleDelivery(consumerTag, envelope, properties, body);
        System.out.println("---------  consumer message ---------");
        System.out.println("consumerTag: " + consumerTag);
        System.out.println("envelope: " + envelope);
        System.out.println("properties: " + properties);
        System.out.println("body: " + new String(body));
        
        // multiple设置为false， 不支持批量签收
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
