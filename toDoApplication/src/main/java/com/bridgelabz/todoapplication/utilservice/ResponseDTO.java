/********************************************************************************* *
 * Purpose: To do Login Registration with the help of MONGODB repository. 
 * Creating a DTO class named ResponseDTO for generating a response so that 
 * we can show the response in swagger or postman.
 * 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice;

import java.io.Serializable;

/**
 * @author Saurav
 *	<p>
 *This class is a DTO class for generating a response so that we can show the response in swagger or postman
 *</p>
 */
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
public ResponseDTO(String message, int status) {
		super();
		this.status = status;
		this.message = message;
	}
/**
 * Method to get the status of the response
 * @return
 */
	public int getStatus() {
		return status;
	}
public ResponseDTO() {
	super();
}
/**
 * Method to set the status of the response
 * @param status
 */
	public void setStatus(int status) {
		this.status = status;
	}
/**
 * Method to get the message of the response
 * @return
 */
	public String getMessage() {
		return message;
	}
/**
 * Method to set the MEssage of the response
 * @param message
 */
	public void setMessage(String message) {
		this.message = message;
	}
}
