package com.example.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishMessageController {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Value("${queue.name}")
	String queueName;
	
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	public boolean publish(@RequestBody Message message) {
		System.out.println("******* Message to be sent is " + message);
		
		boolean isSuccess = false;
		try {
			rabbitTemplate.convertAndSend(queueName, message);
			isSuccess = true;
		} catch (AmqpException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

}
