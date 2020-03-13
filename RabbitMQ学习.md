# RabbitMQ

## 一、主流消息中间件介绍
### 1、ActiveMQ
#### ActiveMQ是Apache出品，最流行的，能力强劲的开源消息总线，并且它是一个完全支持JMS（Java Message Server）规范的消息中间件。
#### 其丰富的API、多种集群构建模式使得他成为业界老牌消息中间件，在中小型企业中应用广泛！
#### MQ衡量指标： 服务性能、数据存储、集群架构

    服务性能：服务性能不是特别好，面对超大规模的并发，会出现阻塞、消息堆积过多、产生延迟等等问题
    数据存储：
    
* 集群架构：Master-Slave模式、NetWork模式

### 2、KAFKA
#### Kafka是Linkedln开源的分布式发布-订阅消息系统，目前属于Apache顶级项目，Kafka主要特点是基于Pull的模式来处理消息消费，追求高吞吐量，一开始的目的就是用于日志收集和传输。0.8版本开始支持复制，不支持事务，对消息的重复、丢失、错误没有严格要求，适合产生大量数据的互联网服务的数据收集业务。

    一开始设计是针对大数据的
    
* 集群架构：

### 3、RocketMQ
#### RocketMQ是阿里开源的消息中间件，目前也是已经孵化为Apache顶级项目，它是纯Java开发，具有高吞吐量、高可用性、适合大规模分布式系统应用的特点。RocketMQ思路起源于Kafka，它对消息的可靠传输及事务性做了优化，目前在阿里集团被管饭应用于交易、充值、流计算、消息推送、日志流式处理、binglog分发等场景

    特点：可以保障消息的顺序性（顺序消费）、有丰富的消息拉取和处理的模式、也可以进行高效的订阅者，进行水平扩展实时消息队列机制、能承载上亿级别的消息堆积能力
    
* 集群架构：Master-Slave模式...

### 4、RabbitMQ
#### RabbitMQ是使用Erlang语言开发的开源消息队列系统，基于AMQP协议来实现。AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。AMQP协议更多用在企业系统内，对数据一致性、稳定性和可靠性要求很高的场景，对性能和吞吐量的要求还在其次。

* 集群架构：


## 二、RabbitMQ核心概念及AMQP协议

#### RabbitMQ是一个开源的消息代理和队列服务器，用来通过普通协议在完全不同的应用之间共享数据，RabbitMQ是使用Erlang语言来编写的，并且RabbitMQ是基于AMQP协议的

### 1、互联网大厂为什么选择RabbitMQ？
* 滴滴、美团、头条、去哪儿、艺龙...
* 开源、性能优秀、稳定性保障
* 提供可靠性消息投递模式（confirm）、返回模式（return）
* 与Spring AMQP完美的整合
* 集群模式丰富，表达式配置，HA模式，镜像队列模型
* 保证数据不丢失的前提做到高可靠性、可用性

### 2、RabbitMQ的高性能之道是如何做到的？
* Erlang语言最初用于交换机领域的架构模式，这样使得RabbitMQ在Broker 之间进行数据交互的性能是非常优秀的
* Erlang的有点：Erlang有着和原生Socket一样的延迟

### 3、什么是AMQP高级消息队列协议？
* AMQP全称：Advanced Message Queuing Protocol
* AMQP翻译：高级消息队列协议
* AMQP定义：是具有现代特征的二进制协议。是一个提供统一消息服务的应用层标准高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计

### 4、AMQP核心概念是什么？
#### （1）、Server：又称Broker（MQ服务器一般都叫Broker），接受客户端的连接，实现AMQP实例服务
#### （2）、Connection：连接，应用程序与Broker的网络连接
#### （3）、Channel：网络通道，几乎所有的操作（数据读写等等）都在Channel中进行，Channel是进行消息读写的通道。客户端可建立多个Channel，每个Channel代表一个会话任务
#### （4）、Message：消息，服务器和应用程序之间传送的数据，由Properties和Body组成。Properties可以对消息进行修饰，比如消息的优先级、延迟等高级特性；Body则就是消息体内容
#### （5）、Virtual host：虚拟地址，用于进行逻辑隔离，最上层的消息路由。一个Virtual Host里面可以有若干个Exchange和Queue，同一个VirtualHost里面不能有相同名称的Exchange或Queue
#### （6）、Exchange：交换机，接收消息，根据路由键转发消息到绑定的队列（生产者直接把消息投递到Exchange上，通过Exchange去进行路由，路由到指定的队列，队列和Exchange有一个绑定的关系）
#### （7）、Binging：Exchange和Queue之间的虚拟连接，binding中可以包含routing key
#### （8）、Routing key：一个路由规则，虚拟机可用它来确定如何路由一个特定消息
#### （9）、Queue：也称为Message Queue，消息队列，保存消息并将它们转发给消费者

### 5、RabbitMQ整体架构模型是什么样子的？

### 6、RabbitMQ消息是如何流转的的？

### 7、RabbitMQ安装与使用

### 8、命令行与管控台

### 9、RabbitMQ消息生产与消费

### 10、RabbitMQ交换机详解

### 11、RabbitMQ队列、绑定、虚拟主机、消息



