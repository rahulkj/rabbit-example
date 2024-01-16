package com.example;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.pivotal.cfenv.core.CfEnv;
import io.pivotal.cfenv.core.CfService;

@Configuration
@Profile("cloud")
public class CloudConfiguration {

	@Value("${rabbitmq.service.name}")
	String rabbitmqServiceName;

	@Value("${queue.name}")
	String queueName;

	@Bean
	ConnectionFactory connectionFactory() {
		CfEnv cfEnv = new CfEnv();
		CfService rabbitmqService = cfEnv.findServiceByName(rabbitmqServiceName);
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUri(rabbitmqService.getCredentials().getUri());
		return connectionFactory;
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		
		Queue queue = new Queue(queueName, true, true, false);
		rabbitAdmin.declareQueue(queue);
		
		return rabbitAdmin;
	}

}
