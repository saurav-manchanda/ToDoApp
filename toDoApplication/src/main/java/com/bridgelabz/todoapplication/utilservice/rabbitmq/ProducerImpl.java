package com.bridgelabz.todoapplication.utilservice.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProducerImpl implements IProducer{
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${saurav.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${saurav.rabbitmq.routingkey}")
	private String routingKey;
	
	public void produceMsg(String to,String subject,String body){
		Mail email=new Mail();
		email.setBody(body);
		email.setSubject(subject);
		email.setTo(to);
		amqpTemplate.convertAndSend(exchange, routingKey, email);
	}
}
