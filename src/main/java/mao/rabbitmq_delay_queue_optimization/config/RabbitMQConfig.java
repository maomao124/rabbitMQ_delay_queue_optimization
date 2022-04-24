package mao.rabbitmq_delay_queue_optimization.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Project name(项目名称)：rabbitMQ_delay_queue_optimization
 * Package(包名): mao.rabbitmq_delay_queue_optimization.config
 * Class(类名): RabbitMQConfig
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/24
 * Time(创建时间)： 17:19
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@Configuration
public class RabbitMQConfig
{
    //交换机
    public static final String X_EXCHANGE = "X";
    //死信交换机
    public static final String DEAD_LETTER_EXCHANGE = "Y";
    //队列
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";

    //死信队列
    public static final String DEAD_QUEUE = "QD";


    /**
     * X exchange direct exchange.
     *
     * @return the direct exchange
     */
    @Bean
    public DirectExchange x_exchange()
    {
        return new DirectExchange(X_EXCHANGE, false, false, null);
    }

    /**
     * Y exchange direct exchange.
     *
     * @return the direct exchange
     */
    @Bean
    public DirectExchange y_exchange()
    {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, false, false, null);
    }

    /**
     * Queue a queue.
     *
     * @return the queue
     */
    @Bean
    public Queue queue_a()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 10000);
        return new Queue(QUEUE_A, false, false, false, map);
    }

    /**
     * Queue b queue.
     *
     * @return the queue
     */
    @Bean
    public Queue queue_b()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 30000);
        return new Queue(QUEUE_B, false, false, false, map);
    }

    /**
     * Queue dead queue.
     *
     * @return the queue
     */
    @Bean
    public Queue queue_dead()
    {
        return new Queue(DEAD_QUEUE, false, false, false);
    }

    /**
     * X bind a binding.
     *
     * @param x_exchange the x exchange
     * @param queue_a    the queue a
     * @return the binding
     */
    @Bean
    public Binding x_bind_a(@Qualifier("x_exchange") DirectExchange x_exchange,
                            @Qualifier("queue_a") Queue queue_a)
    {
        return BindingBuilder.bind(queue_a).to(x_exchange).with("XA");
    }

    /**
     * X bind b binding.
     *
     * @param x_exchange the x exchange
     * @param queue_b    the queue b
     * @return the binding
     */
    @Bean
    public Binding x_bind_b(@Qualifier("x_exchange") DirectExchange x_exchange,
                            @Qualifier("queue_b") Queue queue_b)
    {
        return BindingBuilder.bind(queue_b).to(x_exchange).with("XB");
    }

    /**
     * Y bing qd binding.
     *
     * @param y_exchange the y exchange
     * @param queue_dead the queue dead
     * @return the binding
     */
    @Bean
    public Binding y_bing_qd(@Qualifier("y_exchange") DirectExchange y_exchange,
                             @Qualifier("queue_dead") Queue queue_dead)
    {
        return BindingBuilder.bind(queue_dead).to(y_exchange).with("YD");
    }

    @Bean
    public Queue queue_c()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        return new Queue(QUEUE_C, false, false, false, map);
    }

    @Bean
    public Binding x_bind_c(@Qualifier("x_exchange") DirectExchange x_exchange,
                            @Qualifier("queue_c") Queue queue_c)
    {
        return BindingBuilder.bind(queue_c).to(x_exchange).with("XC");
    }

}
