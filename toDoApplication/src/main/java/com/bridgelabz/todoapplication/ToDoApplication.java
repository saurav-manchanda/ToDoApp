/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication). This application is basically storing and maintaining the notes.
 * this is Starting of the Spring Boot Application.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Saurav
 *         <p>
 *         Starting of the Spring Boot Application. The Annotation used enables
 *         all the autoconfigurations and Scanning all the components.
 *         </p>
 */
@SpringBootApplication
public class ToDoApplication {
	/**
	 * Main Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}
}
