package r01.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 描述：手动获取消息
 * 作者：lyt
 * 日期：2019/6/17 4:48 PM
 * 类名：RabbitMqGetController
 * 版本： version 1.0
 */
@RestController
@RequestMapping(value = "get")
public class RabbitMqGetController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping
    public Object get(String queue) {
        Message message = amqpTemplate.receive(queue);
        if (Objects.nonNull(message)) {
            return new String(message.getBody());
        }
        return queue;
    }
}
