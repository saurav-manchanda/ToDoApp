package com.bridgelabz.todoapplication.utilservice;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;



@Service
public interface MailService {

	public void sendMail(String to,String subject,String body ) throws MessagingException;
}
