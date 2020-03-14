# RabbitMQ

## 一、主流消息中间件介绍
### 1、ActiveMQ
#### ActiveMQ是Apache出品，最流行的，能力强劲的开源消息总线，并且它是一个完全支持JMS（Java Message Server）规范的消息中间件。
#### 其丰富的API、多种集群构建模式使得他成为业界老牌消息中间件，在中小型企业中应用广泛！
#### MQ衡量指标： 服务性能、数据存储、集群架构

    服务性能：服务性能不是特别好，面对超大规模的并发，会出现阻塞、消息堆积过多、产生延迟等等问题
    数据存储：
    
* 集群架构：Master-Slave模式、NetWork模式
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/ActiveMQjiqunjiagou.jpg)

### 2、KAFKA
#### Kafka是Linkedln开源的分布式发布-订阅消息系统，目前属于Apache顶级项目，Kafka主要特点是基于Pull的模式来处理消息消费，追求高吞吐量，一开始的目的就是用于日志收集和传输。0.8版本开始支持复制，不支持事务，对消息的重复、丢失、错误没有严格要求，适合产生大量数据的互联网服务的数据收集业务。

    一开始设计是针对大数据的
    
* 集群架构：
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/Kafkajiqunjiagou.jpg)

### 3、RocketMQ
#### RocketMQ是阿里开源的消息中间件，目前也是已经孵化为Apache顶级项目，它是纯Java开发，具有高吞吐量、高可用性、适合大规模分布式系统应用的特点。RocketMQ思路起源于Kafka，它对消息的可靠传输及事务性做了优化，目前在阿里集团被管饭应用于交易、充值、流计算、消息推送、日志流式处理、binglog分发等场景

    特点：可以保障消息的顺序性（顺序消费）、有丰富的消息拉取和处理的模式、也可以进行高效的订阅者，进行水平扩展实时消息队列机制、能承载上亿级别的消息堆积能力
    
* 集群架构：Master-Slave模式...
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/RocketMQjiqunjiagou.jpg)

### 4、RabbitMQ
#### RabbitMQ是使用Erlang语言开发的开源消息队列系统，基于AMQP协议来实现。AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。AMQP协议更多用在企业系统内，对数据一致性、稳定性和可靠性要求很高的场景，对性能和吞吐量的要求还在其次。

* 集群架构：
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/RabbitMQjiqunjiagou.jpg)


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
#### AMQP协议模型
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/AMQPxieyimoxing.jpg)

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
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/RabbitMQdezhengtijiagou.jpg)
### 6、RabbitMQ消息是如何流转的的？
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/RabbitMQxiaoxiliuzhuan.jpg)
### 7、RabbitMQ安装与使用
    官网地址：http://www.rabbitmq.com
    提前准备：安装Linux必要安装包
    下载RabbitMQ必须安装包
    配置文件修改
    
    准备：
    yum install build-essential openssl openssl-devel unixODBC unixODBC-devel make gcc gcc-c++ kernel-devel m4 ncurses-devel tk tc xz
    
    下载：
    wget www.rabbitmq.com/releases/erlang/erlang-18.3-1.el7.centos.x86_64.rpm
    wget http://repo.iotti.biz/CentOS/7/x86_64/socat-1.7.3.2-5.el7.lux.x86_64.rpm
    wget www.rabbitmq.com/releases/rabbitmq-server/v3.6.5/rabbitmq-server-3.6.5-1.noarch.rpm
    
    安装：(这个过程可能需要升级glibc-2.17，参考这个连接https://cloud.tencent.com/developer/article/1463094)
    rpm -ivh erlang-18.3-1.el7.centos.x86_64.rpm
    rpm -ivh socat-1.7.3.2-1.1.el7.x86_64.rpm
    rpm -ivh rabbitmq-server-3.6.5-1.noarch.rpm
    
    配置文件：
    vim /usr/lib/rabbitmq/lib/rabbitmq_server-3.6.5/ebin/rabbit.app
    比如修改密码、配置等等，例如：loopback_users 中的 <<"guest">>,只保留guest
    服务启动和停止：
    启动 rabbitmq-server start &
    停止 rabbitmqctl app_stop
    
    管理插件：rabbitmq-plugins enable rabbitmq_management6
    访问地址：http://192.168.11.76:15672/

#### （1）、默认端口号：5672
* 这个端口号是通信的端口号，也就是说这是Java端通信的端口号
* 15672：管控台的端口号
* 25672：集群进行通信的端口号
#### （2）、服务的启动：rabbitmq-server start &
    & 表示后台启动
    vim /etc/hostname 修改主机名
    lsof -i:5672    
        如果可以看到beam.smp 9599 rabbitmq   52u  IPv6 223762147      0t0  TCP *:amqp (LISTEN)，就证明RabbitMQ可以了
    
#### （3）、服务的停止：rabbitmqctl app_stop
#### （4）、管理插件：rabbitmq-plugins enable rabbitmq_management
    rabbitmq-plugins list   查看所有插件
    rabbitmq-plugins enable rabbitmq_management   安装管控台
* 管控台默认的端口号是：15672
#### （5）、访问地址：http://XXX:15672/

### 8、命令行与管控台
#### （1）、基础操作
* rabbitmqctl stop_app：关闭应用
* rabbitmqctl start_app：启动应用
* rabbitmqctl status：节点状态
* rabbitmqctl add_user username password：添加用户
* rabbitmqctl list_users：列出所有用户
* rabbitmqctl delete_user username：删除用户
* rabbitmqctl clear_permissions -p vhostpath username：清除用户权限
* rabbitmqctl list_user_permissions username：列出用户权限
* rabbitmqctl change_password username newpassword：修改密码
* rabbitmqctl set_permissions -p vhostpath username ".*" ".*" ".*"：设置用户权限
* rabbitmqctl add_vhost vhostpath：创建虚拟主机
* rabbitmqctl list_vhosts：列出所有虚拟主机
* rabbitmqctl list_permissions -p vhostpath：列出虚拟主机上所有权限
* rabbitmqctl delete_vhost vhostpath：删除虚拟主机
* rabbitmqctl list_queues： 查看所有队列信息
* rabbitmqctl -p vhostpath purge_queue blue：清除队列里的消息

#### （2）、高级操作 （侧重集群运维）
* rabbitmqctl reset：移除所有数据，要在rabbitmqctl stop_app之后使用
* rabbitmqctl join_cluster <clusternode> [--ram]：组成集群命令
* rabbitmqctl cluster_status：查看集群状态
* rabbitmqctl change_clster_node_type disc | ram：修改集群节点的存储形式
* rabbitmqctl forget_cluster_node [--offline] 忘记节点（摘除节点）
* rabbitmqctl rename_cluster_node oldnode1 newnode1 [oldnode2] [newnode2 ...]：修改节点名称

#### （3）、控制台
* Overview 总览


    Queued messages : 如果有消息消费（创建队列）这里就会有一个折线图
    Global counts :
        Connections : 有多少连接
        Channels    : 有多少通信信道
        Exchanges   : 主机数
        Queues      : 队列数
        Consumers   : 消费者数
    Node : 节点的使用状态
    Paths : 配置文件，日志等数据的文件路径
        Config file : /etc/rabbitmq/rabbitmq.config (not found) 
            rpm 安装就没有这个文件    
            源码安装才会配置这个文件
            
    Ports and contexts : 端口
    
    Import / export definitions : 
        可以导入文件定义，可以导入导出RabbitMQ的配置，
        就比如 导出已经配置好的交换机、队列、绑定，然后在导入到新的环境中   
        
        
* Connections 连接
* Channels 网络通信的信道

    
    所有的应用服务和RabbitMQ进行连接都需要建立Channel来进行实际的操作
* Exchange 交换机
![Image](https://github.com/2571138262/RabbitMQ-Learning/tree/master/images-folder/Exchange.png)


    生产者把消息默认投递到的位置
    默认提供的交换机都是以amq.开头的
    direct  直连的方式
    fanout  分发广播的方式
    headers 表示以头的形式
    topic   路由的方式 （重要）
    
    
    Durability 表示MQ是否持久化


### 9、RabbitMQ消息生产与消费

### 10、RabbitMQ交换机详解

### 11、RabbitMQ队列、绑定、虚拟主机、消息



