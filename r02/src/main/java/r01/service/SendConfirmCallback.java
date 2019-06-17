package r01.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 描述：发送消息回调确认
 * 作者：liangyongtong
 * 日期：2019/6/5 5:00 PM
 * 类名：SendConfirmCallback
 * 版本： version 1.0
 */
@Component
public class SendConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {


        System.out.println("msg->" + correlationData);

        System.out.println("ack->" + b);
        System.out.println("cause->" + s);
    }
}
