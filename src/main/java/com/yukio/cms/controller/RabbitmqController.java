package com.yukio.cms.controller;

import com.yukio.common.config.RabbitmqConfig;
import com.yukio.common.model.R;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yukio
 * @create 2022-01-25 14:47
 */
@RequestMapping("/cms/rabbitmq")
public class RabbitmqController {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("/test")
	@ResponseBody
	public R testRabittMq() {
		String msg = "发送邮件消息";
		rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGES_TOPICS_INFORM, "inform.email", msg);
		return R.ok();
	}
}
