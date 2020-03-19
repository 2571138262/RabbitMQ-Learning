package com.baixiaowen.rabbitmqspringbootproducer.producer;

import com.baixiaowen.rabbitmqspringbootproducer.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitSenderTest {
    
    @Autowired
    private RabbitSender rabbitSender;
    
    @Test
    public void testSender1() throws Exception{
        Map<String, Object> properties = new HashMap<>();
        properties.put("num", 123456);
        properties.put("send_time", new Date());
        rabbitSender.send("Hello RabbitMQ For SpringBoot", properties);
    }


    @Test
    public void testSender2() throws Exception{
        Order order = new Order("001", "第一个订单");
        rabbitSender.sendOrder(order);
    }
}