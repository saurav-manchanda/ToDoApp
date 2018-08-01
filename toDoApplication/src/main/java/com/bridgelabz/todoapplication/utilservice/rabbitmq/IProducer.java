/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Producer interface that produces the email. 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 22/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice.rabbitmq;

/**
 * @author bridgelabz
 *         <p>
 *         This is a Producer Interface that produces the email.
 *         </p>
 */
public interface IProducer {
	/**
	 * <p>
	 * This method is taking parameters to,subject and body so as to produce the
	 * email.
	 * </p>
	 * 
	 * @param to
	 * @param subject
	 * @param body
	 */
	public void produceMsg(String to, String subject, String body);
}
