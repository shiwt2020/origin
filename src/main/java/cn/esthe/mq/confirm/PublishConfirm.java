package cn.esthe.mq.confirm;

import cn.esthe.mq.utils.MqConstant;
import cn.esthe.mq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PublishConfirm {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitUtil.getConnection();
        Channel channel = connection.createChannel();

        Map <String,String> pubilshMap =new HashMap<>();
        pubilshMap.put("china.HN.zz","郑州");
        pubilshMap.put("china.shanghai","上海");
        pubilshMap.put("us.delaware","特拉华");
        pubilshMap.put("us.dover","多佛");
        pubilshMap.put("english.London","伦敦");
        pubilshMap.put("test.aa.test","test");
        // 开启confirm监听模式
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {
            // 消息接受
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                //第二个参数代表接受的数据是否为批量接受，一般用不到
                System.out.println("消息已被Broken接受，tag:"+deliveryTag);
            }

            // 消息拒收
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息已被Broken拒收，tag:"+deliveryTag);
            }
        });

        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return r) {
                System.out.println("================");
                System.out.println("return编码" + r.getReplyCode());
                System.out.println("交换机" + r.getExchange() + "路由" + r.getRoutingKey());
                System.out.println("body" + r.getBody());
                System.out.println("================");
            }
        });

        Iterator <Map.Entry <String, String>> it = pubilshMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry <String, String> me = it.next();
            // 交换机名称。routeKey，mandatory 代表消息无法正常投递则return回生产者，如果是false,则将消息直接丢弃
            channel.basicPublish(MqConstant.exchange_publish_confirm, me.getKey(), true,null, me.getValue().getBytes());
        }

        System.out.println("消息发送成功");

        // ！！！通道不能关闭，如果关闭没法去监听消息的是否被接受
        // channel.close();
        // connection.close();

    }
}
