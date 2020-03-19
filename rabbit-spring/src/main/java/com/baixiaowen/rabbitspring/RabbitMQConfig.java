package com.baixiaowen.rabbitspring;

import com.baixiaowen.rabbitspring.adapter.MessageDelegate;
import com.baixiaowen.rabbitspring.convert.TextMessageConverter;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Spring整合RabbitMQ 配置类
 */
@Configuration  // 配置类
@ComponentScan({"com.baixiaowen.rabbitspring.*"})   // 扫描包路径
public class RabbitMQConfig {

    /**
     * 配置注入ConnectionFactory
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        // 配置ConnectionFactory属性
        connectionFactory.setAddresses("183.2.169.17:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    /**
     * 配置注入RabbitAdmin，用来操作RabbitMQ
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 注意：autoStartup必须要设置为true，否则Spring容器不会加载RabbitAdmin类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 针对消费者配置          通过声明的方式添加了一些交换机、队列、和绑定
     * 1、设置交换机类型
     * 2、将队列绑定到交换机
     * FanoutExchange：将消息分发到所有的绑定队列，无routingkey的概念
     * HeaderExchange：通过添加属性key-value匹配
     * DirectExchange：按照routingkey分发到指定队列
     * TopicExchange：多关键字匹配
     *
     * @return
     */
    @Bean
    public TopicExchange exchange001() {
        return new TopicExchange("topic001", true, false);
    }

    @Bean
    public Queue queue001() {
        return new Queue("queue001", true); //队列持久
    }

    @Bean
    public Binding binding001() {
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
    }

    @Bean
    public TopicExchange exchange002() {
        return new TopicExchange("topic002", true, false);
    }

    @Bean
    public Queue queue002() {
        return new Queue("queue002", true); // 队列持久
    }

    @Bean
    public Binding binding002() {
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
    }

    @Bean
    public Queue queue003() {
        return new Queue("queue003", true); // 队列持久
    }

    /**
     * 一个交换机上绑定多个queue
     *
     * @return
     */
    @Bean
    public Binding binding003() {
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
    }

    @Bean
    public Queue queue_image() {
        return new Queue("image_queue", true); // 队列持久
    }

    @Bean
    public Queue queue_pdf() {
        return new Queue("pdf_queue", true);    // 队列持久
    }

    /**
     * 注入消息模板
     * 这个是进行发送消息的关键类
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }


    /**
     * 注入简单消息监听容器，   通过这个配置的注入，就可以实现消息的监听了
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 监听队列
        container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
        // 设置当前的消费者数量
        container.setConcurrentConsumers(1);
        // 设置最大的消费者数量
        container.setMaxConcurrentConsumers(5);
        // 设置是否重回队列         false 不让进行重回队列 
        container.setDefaultRequeueRejected(false);
        // 设置设置签收模式  AUTO 自动签收
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        // 设置消费端的标签策略
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                return queue + "_" + UUID.randomUUID().toString();
            }
        });
        // 设置具体的message的监听
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                String msg = new String(message.getBody());
//                System.out.println("-------------------- 消费者： " + msg);
//            }
//        });

        /**
         * 1 
         // 这里不直接使用MessageListener监听器来监听了，使用一个adapter，适配器 参数是Object，任意类型
         // 适配器方式，默认是有子弟的方法名字的，handleMessage
         // 可以自己制定一个方法的名字， consumerMessage
         // 也可以添加一个转换器，从字节数组转换为String
         MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
         // 自定义adapter处理方法
         adapter.setDefaultListenerMethod("consumerMessage");
         // 添加Message转换器（自定义）
         adapter.setMessageConverter(new TextMessageConverter());
         // 设置适配器
         container.setMessageListener(adapter);
         // 设置Listener是否外露
         //        container.setExposeListenerChannel(true);
         */

        /**
         * 2 适配器方式：我们的队列名称 和 方法名称也可以进行 一一 的匹配

         MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
         adapter.setMessageConverter(new TextMessageConverter());
         Map<String, String> queueOrTagToMethodNum = new HashMap<>();
         queueOrTagToMethodNum.put("queue001", "method1");
         queueOrTagToMethodNum.put("queue002", "method2");
         adapter.setQueueOrTagToMethodName(queueOrTagToMethodNum);
         container.setMessageListener(adapter);
         */

        // 1.1 支持json格式的转换器
        /**
         *
         
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumerMessage");
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        adapter.setMessageConverter(jackson2JsonMessageConverter);
        container.setMessageListener(adapter);
         */

        return container;
    }


}
