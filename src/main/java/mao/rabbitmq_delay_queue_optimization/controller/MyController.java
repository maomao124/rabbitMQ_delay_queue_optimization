package mao.rabbitmq_delay_queue_optimization.controller;

import mao.rabbitmq_delay_queue_optimization.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project name(项目名称)：rabbitMQ_delay_queue_optimization
 * Package(包名): mao.rabbitmq_delay_queue_optimization.controller
 * Class(类名): MyController
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/24
 * Time(创建时间)： 17:21
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@RestController
public class MyController
{
    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test1/{msg}")
    @ResponseBody
    public String test1(@PathVariable String msg)
    {
        log.info("开始发送消息(XA)：" + msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE, "XA", msg);
        return msg;
    }

    @GetMapping("/test2/{msg}")
    @ResponseBody
    public String test2(@PathVariable String msg)
    {
        log.info("开始发送消息(XB)：" + msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE, "XB", msg);
        return msg;
    }

    @GetMapping("/test3/{msg}")
    @ResponseBody
    public String test3(@PathVariable String msg)
    {
        log.info("开始发送消息(XC)：" + msg);

        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE, "XC", "延迟10秒：" + msg, new MessagePostProcessor()
        {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException
            {
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        });
        return msg;
    }

    @GetMapping("/test4/{msg}")
    @ResponseBody
    public String test4(@PathVariable String msg)
    {
        log.info("开始发送消息(XC)：" + msg);

        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE, "XC", "延迟30秒：" + msg, new MessagePostProcessor()
        {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException
            {
                message.getMessageProperties().setExpiration("30000");
                return message;
            }
        });
        return msg;
    }
}
