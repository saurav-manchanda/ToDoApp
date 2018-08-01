package com.bridgelabz.todoapplication.utilservice.rabbitmq;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.todoapplication.utilservice.MailService;

@Component
public class ConsumerImpl implements IConsumer {

	@Autowired
	MailService mailService;
	
	@Override
	@RabbitListener(queues = "${saurav.rabbitmq.queue}")
	public void recievedMessage(Mail email) throws MessagingException {
		String to=email.getTo();
		String subject=email.getSubject();
		String body=email.getBody();
		mailService.sendMail(to,subject,body);
	}

}
