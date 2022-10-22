package base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class RadioConsumer {
    public static void main(String[] args) throws MQClientException {
        //1.创建一个接收消息的对象Consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.设定接收的命名服务器地址
        consumer.setNamesrvAddr("106.15.250.225:9876");
        //3.设置接收消息对应的topic,对应的sub标签为任意
        consumer.subscribe("topic1", "*");
        //设置当前消费者的消费模式（默认模式：负载均衡）
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        //设置当前消费者的消费模式（广播模式）
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //3.开启监听，用于接收消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //遍历消息
                for (MessageExt msg : list) {
                    System.out.println("收到消息：" + msg);
                    System.out.println("消息是：" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //4.启动接收消息的服务
        consumer.start();
        System.out.println("接受消息服务已经开启！");

        //5 不要关闭消费者！
    }
}
