package r01.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：发送
 * 作者：liangyongtong
 * 日期：2019/6/3 3:51 PM
 * 类名：RabbitMqSendController
 * 版本： version 1.0
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/send")
public class RabbitMqSendController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 发送消息
    @GetMapping
    public void sendMsg(String e, String r, String v) {
        rabbitTemplate.convertAndSend(e, r, v);
    }

    @GetMapping("/fanout")
    public void send() {
        rabbitTemplate.convertAndSend("f_exchange", "", "123test");
    }

    @GetMapping("/topic")
    public void sendTopic(@RequestParam(required = false, defaultValue = "") String routkey, @RequestParam(defaultValue = "msg") String msg) {
        rabbitTemplate.convertAndSend("t_exchange", routkey, msg);
    }

    @GetMapping("direct")
    public void sendDirect(String rout) {
        rabbitTemplate.convertAndSend("d_exchange", rout, rout);
    }
}
