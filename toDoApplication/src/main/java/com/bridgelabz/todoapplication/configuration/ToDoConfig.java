/********************************************************************************* *
 * Purpose: To do Login Registration with the help of MONGODB repository. 
 * Creating a class which is used for encrypting the password in the code.
 * 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.todoapplication.utilservice.ObjectMapper.ObjectMapping;

/**
 * @author Saurav
 *         <p>
 *         a class which is used for encrypting the password in the code.
 *         </p>
 */
@Configuration
public class ToDoConfig {
	/**
	 * @return encoder
	 *         <p>
	 *         This method is to encode the password with the use of
	 *         BcrypPasswordEncoder
	 *         </p>
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	@Bean
	public ObjectMapping objectmapping() {
		return new ObjectMapping();
	}
}
