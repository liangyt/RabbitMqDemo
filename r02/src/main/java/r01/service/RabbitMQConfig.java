package r01.service;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：配置rabbitmq
 * 作者：liangyongtong
 * 日期：2019/6/5 5:22 PM
 * 类名：RabbitMQConfig
 * 版本： version 1.0
 */
@Configuration
public class RabbitMQConfig {
    @Autowired
    private SendConfirmCallback sendConfirmCallback;
    @Autowired
    private SendReturnCallback sendReturnCallback;

    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();



        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(sendConfirmCallback);
        rabbitTemplate.setReturnCallback(sendReturnCallback);

        return rabbitTemplate;
    }
}
