package com.baixiaowen.rabbitmqspringbootproducer.producer;

import com.baixiaowen.rabbitmqspringbootproducer.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSender {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 消息确认，保证MQ的可靠性投递，得到Broker的响应，
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            System.err.println("ack: " + ack);
            if (!ack){
                System.err.println("异常处理");
            }
        }
    };

    /**
     * 消息确认，保证MQ的可靠性投递，监听不可路由的消息
     */
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.err.println("return exchange:" + exchange 
                    + ", routingKey:" + routingKey + ", replyCode:" 
                    + replyCode + ", replyText:" + replyText);
        }
    };
    
    /**
     * 发送消息
     * @param message
     * @param properties
     * @throws Exception
     */
    public void send(Object message, Map<String, Object> properties) throws Exception{
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);
        // 在rabbitTemplate上设置消息确认
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData cd = new CorrelationData();
        cd.setId("1234567890");   // id + 时间戳 全局唯一
        rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", msg, cd);
    }

    /**
     * 发送消息
     * @param order
     * @throws Exception
     */
    public void sendOrder(Order order) throws Exception{
        // 在rabbitTemplate上设置消息确认
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData cd = new CorrelationData();
        cd.setId("0987654321");   // id + 时间戳 全局唯一
        rabbitTemplate.convertAndSend("exchange-2", "springboot.abcdef", order, cd);
    }
    
    
}
