package mao.rabbitmq_delay_queue_optimization.consumer;

import mao.rabbitmq_delay_queue_optimization.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Project name(项目名称)：rabbitMQ_delay_queue_optimization
 * Package(包名): mao.rabbitmq_delay_queue_optimization.consumer
 * Class(类名): Consumer1
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/24
 * Time(创建时间)： 17:22
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@Component
public class Consumer1
{
    private static final Logger log = LoggerFactory.getLogger(Consumer1.class);

    @RabbitListener(queues = {RabbitMQConfig.DEAD_QUEUE})
    public void getMessage(String message)
    {
        log.info("接收到消息：" + message);
    }
}
