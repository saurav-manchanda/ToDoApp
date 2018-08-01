/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Configuring the Rabbit MQ
 * @author Saurav Manchanda
 * @version 1.0
 * @since 22/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Saurav
 *         <p>
 *         Thos class is for configuring the Rabbit MQ in the application. We
 *         are using the same for sending an email in our respective application
 *         </p>
 */
@Configuration
public class RabbitMQConfig {
	@Value("${saurav.rabbitmq.queue}")
	String queueName;

	@Value("${saurav.rabbitmq.exchange}")
	String exchange;

	@Value("${saurav.rabbitmq.routingkey}")
	private String routingkey;
/**
 * This method is for creating the bean for Queue
 * @return
 */
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
/**
 * This method is for creating the bean for Direct Exchange
 * @return
 */
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}
/**
 * This method is for creating the bean for Binding
 * @param queue
 * @param exchange
 * @return
 */
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}
/**
 * This method is for creating the bean for MessageConverter
 * @return
 */
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
/**
 * This method is for creating a bean for Amqp Template
 * @param connectionFactory
 * @return
 */
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

}
