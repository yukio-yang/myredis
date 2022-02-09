package com.yukio.common.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yukio
 * @create 2022-01-25 14:32
 */
@Configuration
public class RabbitmqConfig {
	public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
	public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
	public static final String EXCHANGES_TOPICS_INFORM = "exchanges_topics_inform";

	//可以匹配inform.email inform.email.sms
	public static final String ROUTINGKEY_EMAIL = "inform.#.email.#";

	//可以匹配inform.sms inform.email.sms
	public static final String ROUTINGKEY_SMS = "inform.#.sms.#";

	//声明交换机
	@Bean("EXCHANGE_TOPICS_INFORM")
	public Exchange EXCHANGE_TOPICS_INFORM() {
		return ExchangeBuilder.topicExchange(EXCHANGES_TOPICS_INFORM).durable(true).build();
	}

	//声明邮箱队列
	@Bean("QUEUE_INFORM_EMAIL")
	public Queue QUEUE_INFORM_EMAIL() {
		return new Queue(QUEUE_INFORM_EMAIL);
	}

	//声明短信队列
	@Bean("QUEUE_INFORM_SMS")
	public Queue QUEUE_INFORM_SMS() {
		return new Queue(QUEUE_INFORM_SMS);
	}

	//绑定交换机和邮箱队列
	@Bean
	public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier("QUEUE_INFORM_EMAIL") Queue queue, @Qualifier("EXCHANGE_TOPICS_INFORM") Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
	}

	//绑定交换机和短信队列
	@Bean
	public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier("QUEUE_INFORM_SMS") Queue queue, @Qualifier("EXCHANGE_TOPICS_INFORM") Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
	}

}
