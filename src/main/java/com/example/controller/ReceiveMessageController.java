package com.example.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiveMessageController {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Value("${queue.name}")
	String queueName;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public Message receive() {
		Message message = null;
		try {
			message = (Message) rabbitTemplate.receiveAndConvert(queueName);
		} catch (AmqpException e) {
			e.printStackTrace();
		}
		return message;
	}

}
