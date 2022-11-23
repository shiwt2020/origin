package cn.esthe.mq.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("172.16.131.65");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("xiaoming");
        connectionFactory.setPassword("xiaoming");
        connectionFactory.setVirtualHost("/xm");

        // 获取tcp长连接
        return connectionFactory.newConnection();
    }
}
