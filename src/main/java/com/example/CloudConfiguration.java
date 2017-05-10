package com.example;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.common.AmqpServiceInfo;
import org.springframework.cloud.service.messaging.RabbitConnectionFactoryCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("cloud")
public class CloudConfiguration {

	@Value("${rabbitmq.service.name}")
	String rabbitmqServiceName;

	@Value("${queue.name}")
	String queueName;

	@Bean
	ConnectionFactory connectionFactory() {
		Cloud cloud = new CloudFactory().getCloud();
		AmqpServiceInfo serviceInfo = (AmqpServiceInfo) cloud.getServiceInfo(rabbitmqServiceName);
		return new RabbitConnectionFactoryCreator().create(serviceInfo, null);
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
