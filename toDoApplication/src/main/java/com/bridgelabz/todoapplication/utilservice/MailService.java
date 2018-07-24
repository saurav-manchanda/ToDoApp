/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating an interface Mail Service for giving emailing functionality  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

/**
 * @author Saurav
 *         <p>
 *         This class is Mail Service having a method sendMail() which is having
 *         to, subject and body. This method is called by the rabbitMQ
 *         <p>
 */
@Service
public interface MailService {
	/**
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 *             <p>
	 *             This method sendMail() which is having to, subject and body. This
	 *             method is called by the rabbitMQ
	 *             </p>
	 */
	public void sendMail(String to, String subject, String body) throws MessagingException;
}
