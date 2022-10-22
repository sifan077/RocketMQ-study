package base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者
 * 消费者接收，运行多个此程序可以运行多个消费者
 * @author 思凡
 * @date 2022/10/22
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        /**
         1. 谁来发？
         2. 发给谁？
         3. 怎么发？
         4. 发什么？
         5. 发的结果是什么？
         6. 打扫战场
         **/

        //1.创建一个接收消息的对象Consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.设定接收的命名服务器地址
        consumer.setNamesrvAddr("106.15.250.225:9876");
        //3.设置接收消息对应的topic,对应的sub标签为任意
        consumer.subscribe("topic1", "*");

        //接收消息的时候，除了制定topic，还可以指定接收的tag,*代表任意tag
        // consumer.subscribe("topic6","tag1 || tag2");

        //3.开启监听，用于接收消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //遍历消息
                for (MessageExt msg : list) {
                    System.out.println("收到消息的详细信息为" + msg);
                    System.out.println("收到消息的内容为：" + new String(msg.getBody()));
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