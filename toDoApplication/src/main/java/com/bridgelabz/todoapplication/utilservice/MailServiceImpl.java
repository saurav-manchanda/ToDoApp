package com.bridgelabz.todoapplication.utilservice;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;



@Component
public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender javaMailSender;


	@Override
	public void sendMail(String to,String subject,String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body);
		
		javaMailSender.send(message);
		
	}
}
