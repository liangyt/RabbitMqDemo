package r01.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 描述：消息未路由回调
 * 作者：liangyongtong
 * 日期：2019/6/5 5:08 PM
 * 类名：SendReturnCallback
 * 版本： version 1.0
 */
@Slf4j
@Component
public class SendReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("msg->" + new String(message.getBody()) + " ; replycode->" + i + "; replyText->" + s + "; exchange->" + s1 + "; routkey->" + s2);
    }
}
