/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a TODOExceptionHandler,  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Saurav
 *<p>
 *To handle generic exceptions 
 *</p>
 */
@ControllerAdvice
public class ToDoExceptionHandler {
	/**
	 * @param exception
	 * @return response with Http status
	 * <p><b>To handle generic exceptions </b></p>	
	 **/
	@ExceptionHandler(ToDoException.class)
	public ResponseEntity<ResponseDTO> todoExceptionHandler(ToDoException exception) {
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> exceptionHandler(Exception exception) {
		ResponseDTO response = new ResponseDTO();
		response.setMessage("WE ARE GETTING IO EXCEPTION");
		response.setStatus(-1);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
	}
}
