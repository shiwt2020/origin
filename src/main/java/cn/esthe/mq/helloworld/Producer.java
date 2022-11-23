package cn.esthe.mq.helloworld;

import cn.esthe.mq.utils.MqConstant;
import cn.esthe.mq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("开始发送消息=========");
        // 获取rabbit连接
        Connection connection = RabbitUtil.getConnection();
        Channel channel = connection.createChannel();

        // 队列名称、
        // 是否持久化、false对应不持久化,mq停掉就会丢失
        // 是否私有化、false所有消费者都可以访问，true第一次拥有它的消费者才能访问
        // 是否自动删除，false 代表连接停掉后不自动删除
        // 其他参数
        channel.queueDeclare(MqConstant.queue_hello_world, false, false, false, null);
        String message = "hello_xiao_ming";
        System.out.println("消息内容" + message);
        // exchange交换机信息：类型direct(直接)、topic(主题)、fanout(扇形)、headers(头)，用于接受、分配消息
        // 队列名称、额外参数、传递的消息
        channel.basicPublish("", "xm_helloWorld", null, message.getBytes());
        channel.close();
        connection.close();
        System.out.println("数据发送成功==========");
    }
}
