package com.baixiaowen.rabbitmqlearning.ACK与重回队列;

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
        System.out.println("body: " + new String(body));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 这里模拟场景，如果num == 0 就模拟消息消费失败，在手动签收的模式下，需要手动的设置channel.basicNack()
//        if ((Integer) properties.getHeaders().get("num") == 0){
//            // channel.basicNack()代表的就是消费失败了，requeue = true 代表的是重回队列（重回到队列的最尾部）
//            channel.basicNack(envelope.getDeliveryTag(), false, true);
//        } else {
            // multiple代表批量设置为false， 意思为不支持批量签收
            // channel.basicAck() 代表这个消息正常消费了，客户端正常签收
            channel.basicAck(envelope.getDeliveryTag(), false);   
//        }
    }
}
