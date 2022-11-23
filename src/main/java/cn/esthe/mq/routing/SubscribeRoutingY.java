package cn.esthe.mq.routing;

import cn.esthe.mq.utils.MqConstant;
import cn.esthe.mq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SubscribeRoutingY {
    public static void main(String[] args)throws IOException, TimeoutException {
        Connection connection = RabbitUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列信息
        channel.queueDeclare(MqConstant.queue_subscribe_routing_y,false,false,false,null);

        // 队列绑定交换机
        channel.queueBind(MqConstant.queue_subscribe_routing_y,MqConstant.exchange_routing_direct,"china.anhui");

        channel.basicQos(1);


        channel.basicConsume(MqConstant.queue_subscribe_routing_y,false,new DefaultConsumer(channel){
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
