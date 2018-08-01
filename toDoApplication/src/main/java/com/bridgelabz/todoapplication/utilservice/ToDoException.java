/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a common Exception named toDoException which is particular for our Application  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice;

/**
 * @author Saurav
 *         <p>
 *         This class is ToDoException extends Exception
 *         </p>
 */
public class ToDoException extends Exception {

	private static final long serialVersionUID = 1L;
/**
 * Constructor having message as parameter
 * @param message
 */
	public ToDoException(String message) {
		super(message);
	}
}