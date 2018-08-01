/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication). This application is basically storing and maintaining the notes.
 * Creating an interceptor which is called before command goes to controller.This is for configuring all the interceptors
 * @author Saurav Manchanda
 * @version 1.0
 * @since 30/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.todoapplication.utilservice.interceptor.LoggerInterceptor;
import com.bridgelabz.todoapplication.utilservice.interceptor.ToDoInterceptor;

/**
 * @author Saurav
 *         <p>
 *         This is an Interceptor config class which is used to configure the
 *         interceptors in the ToDoApplication
 *         </p>
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	LoggerInterceptor loggerInterceptor;
	@Autowired
	ToDoInterceptor toDoInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 * For adding the loggerInterceptor in the application
		 */
		registry.addInterceptor(loggerInterceptor);
		/**
		 * For adding the ToDoInterceptor
		 */
		registry.addInterceptor(toDoInterceptor).addPathPatterns("/notes/**");
	}
}
