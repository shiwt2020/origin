package cn.esthe.mq.utils;

public class MqConstant {
    // queue  helloWorld模式
    public static String  queue_hello_world="xm_helloWorld";

    // 发布订阅模式
    public static String  queue_subscribe_x="xm_subscribe_x";
    public static String  queue_subscribe_y="xm_subscribe_y";
    public static String  queue_subscribe_z="xm_subscribe_z";

    // routing模式
    public static String  queue_subscribe_routing_x="xm_subscribe_routing_x";
    public static String  queue_subscribe_routing_y="xm_subscribe_routing_y";

    // topic模式
    public static String  queue_subscribe_topic_x="xm_subscribe_topic_x";
    public static String  queue_subscribe_topic_y="xm_subscribe_topic_y";

    // confirm 测试生产者 发送的消息是否被接收
    public static String  queue_publish_confirm_x="xm_confirm_topic_x";
    public static String  queue_publish_confirm_y="xm_confirm_topic_y";


    // exchange
    // 发布-订阅模式
    public static String  exchange_publish="xm_exchange";

    // 路由模式
    public static String  exchange_routing_direct="xm_exchange_direct";

    // topic模式
    public static String  exchange_routing_topic="xm_exchange_topic";

    // confirm 测试生产者 发送的消息是否被接收
    public static String  exchange_publish_confirm="xm_confirm_topic";

}
