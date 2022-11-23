package cn.esthe.mq.routing;

import cn.esthe.mq.utils.MqConstant;
import cn.esthe.mq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class PublishRouting {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitUtil.getConnection();
        Channel channel = connection.createChannel();

        Map <String,String> map =new HashMap<>();
        map.put("china.anhui","合肥");
        map.put("china.shandong","济南");
        map.put("china.shanghai","上海");

        /**
         *  交换机类型，交换机只负责转发消息，不存储消息，如果没有队列与Exchange绑定，则消息会丢失
         *          fanout, 广播,将消息交给所有绑定交换机的队列
         *          topic, 通配符，把消息交给符合routing pattern 的队列
         *          direct,定向，将消息交给符合routing key 队列
         *          header,
         */
        Iterator <Map.Entry <String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry <String, String> me = iterator.next();
            // 交换机名称。routeKey，其他参数
            channel.basicPublish(MqConstant.exchange_routing_direct, me.getKey(), null, me.getValue().getBytes());
        }


        System.out.println("消息发送成功");
        channel.close();
        connection.close();

    }
}
