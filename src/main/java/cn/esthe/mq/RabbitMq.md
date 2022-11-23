Rabbit Mq 消息确认机制
1,RabbitMq 在消息传递的过程中充当了代理人（Broker）的角色，
           那生产者(Producer)如何确认消息被正确的投递到(Broker)中呢
2,RabbitMq 提供了监听（Listener）来接受消息的传递，消息确认设计两种状态：Confirm Return
     Confirm 代表生产者将消息送到Broker时产生的状态，后续会出现两种情况
         - ack 代表Broker 已经将数据接收
         - nack 代表Broker拒收消息，原有有多种 队列已满，限流，IO异常
     return 代表消息被Broker 正常接受（ack）后，但是Broker没有对应的队列进行投递时产生的状态，消息被退回给生产者
    注意：上述状只代表生产者与Broker之间的关系，跟消费者无关(channel.basicAck)
    
hello world
work queue
publish-subscriber  exchange
routing     exchange-routing
topic       通配符

