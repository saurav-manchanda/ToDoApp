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
import org.springframework.stereotype.Component;

import com.bridgelabz.todoapplication.utilservice.ObjectMapper.ObjectMapping;

/**
 * @author Saurav
 *         <p>
 *         a class which is used for encrypting the password in the code.
 *         </p>
 */
@Configuration
@Component
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
	/**
	 * This method is for creating the bean for Model mapper
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	/**
	 * This method is creating a bean for our class ObjectMapping
	 * @return
	 */
	@Bean
	public ObjectMapping objectmapping() {
		return new ObjectMapping();
	}
//	@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
//		return jedisConFactory;
//	}
//
//	/**
//	 * @return redis template
//	 */
//	@Bean
//	public RedisTemplate<String, User> redisTemplate() {
//		RedisTemplate<String, User> redisTemplate = new RedisTemplate<String, User>();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
//		return redisTemplate;
//	}
}
