package r01.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
    public void consumerc(Message msg) {
        System.out.println("c => " + new String(msg.getBody()));
    }
}
