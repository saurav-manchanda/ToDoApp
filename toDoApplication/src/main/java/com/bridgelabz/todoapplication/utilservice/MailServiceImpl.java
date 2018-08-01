/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a MailService implementation class overriding the function in IMailService Interface 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


/**
 * @author bridgelabz
 *         <p>
 *         This is a MailService implementation class overriding the function in
 *         IMailService Interface
 *         </p>
 */
@Component
public class MailServiceImpl implements MailService {
	@Autowired
	private JavaMailSender javaMailSender;
	public static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	/**
	 * This method is for sending the email to the respective to,subject and body
	 */
	@Override
	public void sendMail(String to, String subject, String body) throws MessagingException {
		logger.info("inside sendMail method");
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body);
		javaMailSender.send(message);
		logger.info("Mail is sent");
	}
}
