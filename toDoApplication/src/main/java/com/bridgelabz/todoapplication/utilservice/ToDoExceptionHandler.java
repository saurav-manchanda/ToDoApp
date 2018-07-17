package com.bridgelabz.todoapplication.utilservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.todoapplication.userservice.model.ResponseDTO;

@ControllerAdvice
public class ToDoExceptionHandler {
	/**
	 * @param exception
	 * @return response with Http status
	 * <p><b>To handle exception occur at registration time</b></p>	
	 * */
	@ExceptionHandler(ToDoException.class)
	public ResponseEntity<ResponseDTO> registrationExceptionHandler(ToDoException exception) {
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);

	}

}

