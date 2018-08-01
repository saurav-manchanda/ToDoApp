/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Mail class having fields such as to,subject and body so as to compose a mail and forward it to a subsequent to. 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 22/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice.rabbitmq;

/**
 * @author Saurav
 *         <p>
 *         The Mail class having fields such as to,subject and body so as to
 *         compose a mail and forward it to a subsequent to.
 *         </p>
 */
public class Mail {
	private String to;
	private String subject;
	private String body;
/**
 * Method to get the to email id where you want to send the email
 * @return
 */
	public String getTo() {
		return to;
	}
/**
 * Method to set the To where you want to send the email
 * @param to
 */
	public void setTo(String to) {
		this.to = to;
	}
/**
 * Method to get the subject
 * @return
 */
	public String getSubject() {
		return subject;
	}
/**
 * Method to set the subject
 * @param subject
 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
/**
 * Method to get the body
 * @return
 */
	public String getBody() {
		return body;
	}
/**
 * Method to set the body
 * @param body
 */
	public void setBody(String body) {
		this.body = body;
	}

}
