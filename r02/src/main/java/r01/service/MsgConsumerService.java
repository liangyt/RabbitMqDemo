package r01.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 描述：消息消费
 * 作者：liangyongtong
 * 日期：2019/6/3 5:04 PM
 * 类名：MsgConsumerService
 * 版本： version 1.0
 */
@Component
public class MsgConsumerService {

    /**
     * 监听队列
     * @param msg
     */
    @RabbitListener(queues = "a")
    public void consumer01(String msg) {
        System.out.println("a01 => " + msg);
    }

    @RabbitListener(queues = "a")
    public void consumer0201(Message msg) {

        System.out.println("a02 => " + new String(msg.getBody()));
    }

    @RabbitListener(queues = "b")
    public void consumer0202(Message msg) {
        System.out.println("b => " + new String(msg.getBody()));
    }

    @RabbitListener(queues = "c")
    public void consumerc(Message msg, Channel channel) throws Exception {

        System.out.println("c => " + new String(msg.getBody()));

        // 设置为 false 表示确认当前这一条消息
//            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);

        // 拒绝消息，每次可以拒绝一条或者多条消息，第二个参数为 false 表示只拒绝当前编号的消息，为 ture则表示拒绝当前编号之前的所有未被确认的消息, 第三个参数是否重回队列
//            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
        // 拒绝消息，每次只能拒绝一条; 第一个参数消息编号, 第二个参数为false 表示把消息从队列中移除，为 ture 表示把消息重新放回队列中, 然后发给消费者
//            channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
