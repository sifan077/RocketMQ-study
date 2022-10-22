package base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * 生产商
 * 一对一，一对多的生产者
 * @author 思凡
 * @date 2022/10/22
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        /**
         1. 谁来发？
         2. 发给谁？
         3. 怎么发？
         4. 发什么？
         5. 发的结果是什么？
         6. 打扫战场
         **/

        //1.创建一个发送消息的对象Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.设定发送的命名服务器地址
        producer.setNamesrvAddr("106.15.250.225:9876");
        //3.1启动发送的服务
        producer.start();
        //4.创建要发送的消息对象,指定topic，指定内容body
//        Message msg = new Message("topic1", "hello rocketmq".getBytes(StandardCharsets.UTF_8));

        // 创建指定tag的消息，消费者便于过滤
        // 创建 tag 为 tag2 的消息
        // Message msg = new Message("topic6","tag2",("消息过滤按照tag：hello rocketmq 2").getBytes("UTF-8"));

        //3.2发送消息
//        SendResult result = producer.send(msg);
//        System.out.println("返回结果：" + result);
        for (int i = 0; i < 10; i++) {
            //4.创建要发送的消息对象,指定topic，指定内容body
            Message msg = new Message("topic1", ("hello rocketmq" + i).getBytes(StandardCharsets.UTF_8));
            //3.2发送消息
            // 消息类别
            //拷贝producer
            SendResult result = producer.send(msg);
            System.out.println("返回结果：" + result);

            //异步消息
//            producer.send(msg, new SendCallback() {
//                //表示成功返回结果
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println(sendResult);
//                }
//                //表示发送消息失败
//                @Override
//                public void onException(Throwable throwable) {
//                    System.out.println(throwable);
//                }
//            });
//            System.out.println("消息"+i+"发完了，做业务逻辑去了！");

            // 单向消息，不需要回执
            // producer.sendOneway(msg);

            // 延时消息
            // Message msg = new Message("topic3",("延时消息：hello rocketmq "+i).getBytes("UTF-8"));
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
//            msg.setDelayTimeLevel(3);
//            SendResult result = producer.send(msg);
//            System.out.println("返回结果："+result);

            // 批量消息

//            List<Message> msgList = new ArrayList<Message>();
//            Message msg1 = new Message("topic1", ("hello rocketmq1").getBytes("UTF-8"));
//            Message msg2 = new Message("topic1", ("hello rocketmq2").getBytes("UTF-8"));
//            Message msg3 = new Message("topic1", ("hello rocketmq3").getBytes("UTF-8"));
//
//            msgList.add(msg1);
//            msgList.add(msg2);
//            msgList.add(msg3);
//            SendResult result = producer.send(msgList);
        }
        //5.关闭连接
        producer.shutdown();
    }
}
