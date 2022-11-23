package cn.esthe.mq.pubsub;

import cn.esthe.mq.utils.MqConstant;
import cn.esthe.mq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.util.Scanner;

public class Publish {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitUtil.getConnection();
        Channel channel = connection.createChannel();

        String input=new Scanner(System.in).next();

        // 交换机名称。routeKey 暂时用不到，其他参数
        // 交换机类型，交换机只负责转发消息，不存储消息，如果没有队列与Exchange绑定，则消息会丢失
        // fanout, 广播,将消息交给所有绑定交换机的队列
        // topic, 通配符，把消息交给符合routing pattern 的队列
        // direct,定向，将消息交给符合routing key 队列
        // header,
        channel.basicPublish(MqConstant.exchange_publish,"",null,input.getBytes());

        channel.close();
        connection.close();

    }
}
