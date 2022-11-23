package cn.esthe.mq.helloworld;

import cn.esthe.mq.utils.MqConstant;
import cn.esthe.mq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtil.getConnection();
        Channel channel = connection.createChannel();

        // 队列名称、
        // 是否持久化、false对应不持久化,mq停掉就会丢失
        // 是否私有化、false所有消费者都可以访问，true第一次拥有它的消费者才能访问
        // 是否自动删除，false 代表连接停掉后不自动删除
        // 其他参数
        channel.queueDeclare(MqConstant.queue_hello_world, false, false, false, null);

        // 队列名称
        // 代表是否自动确认收到消息，false代表手动编程确认消息，mq推荐做法
        // DefaultConsumer 实现类
        channel.basicConsume(MqConstant.queue_hello_world, false, new Receive(channel));
    }
}

class Receive extends DefaultConsumer {
    private Channel channel;

    public Receive(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body);
        System.out.println("接受到的消息:" + message);
        System.out.println("tag标签:" + envelope.getDeliveryTag());
        // false 表示签收当前的消息，true 签收所有未签收的消息
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
