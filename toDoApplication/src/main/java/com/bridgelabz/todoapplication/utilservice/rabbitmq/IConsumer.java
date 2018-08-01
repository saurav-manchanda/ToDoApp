/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication). This application is basically storing and maintaining the notes.
 * This is the interface for our consumer. The consumer consumers the message produced by the producer
 * @author Saurav Manchanda
 * @version 1.0
 * @since 22/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice.rabbitmq;

import javax.mail.MessagingException;

/**
 * @author Saurav
 *         <p>
 *         This interface is for receiving the email produced by the email
 *         </p>
 */
public interface IConsumer {
	/**
	 * <p>
	 * the method is taking the email from the producer.
	 * </p>
	 * 
	 * @param email
	 * @throws MessagingException
	 */
	void recievedMessage(Mail email) throws MessagingException;
}
