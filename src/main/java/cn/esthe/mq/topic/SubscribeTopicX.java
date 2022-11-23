package cn.esthe.mq.topic;

import cn.esthe.mq.utils.MqConstant;
import cn.esthe.mq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SubscribeTopicX {
    public static void main(String[] args)throws IOException, TimeoutException {
        Connection connection = RabbitUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列信息
        channel.queueDeclare(MqConstant.queue_subscribe_topic_x,false,false,false,null);

        // 指定交换机与指定队列的关系以及routingKey的关系，可以绑定多个routingKey
        channel.queueBind(MqConstant.queue_subscribe_topic_x,MqConstant.exchange_routing_topic,"earth.china.shandong");
        channel.queueBind(MqConstant.queue_subscribe_topic_x,MqConstant.exchange_routing_topic,"earth.china.shanghai");
        channel.queueBind(MqConstant.queue_subscribe_topic_x,MqConstant.exchange_routing_topic,"earth.china.anhui");

        // 每次取完一条 在取下一条消息
        channel.basicQos(1);


        channel.basicConsume(MqConstant.queue_subscribe_topic_x,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到的发布信息:"+new String(body));
                System.out.println(envelope.getDeliveryTag());
                // 手动ack机制  第二个参数为false mq推荐做法
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });

    }
}
