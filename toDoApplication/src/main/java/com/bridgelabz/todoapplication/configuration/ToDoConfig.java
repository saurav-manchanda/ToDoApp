/********************************************************************************* *
 * Purpose: To do Login Registration with the help of MONGODB repository. 
 * Creating a class which is used for configuration purpose
 * 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.configuration;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.todoapplication.utilservice.ObjectMapper.ObjectMapping;

/**
 * @author Saurav
 *         <p>
 *         a class which is used for configuration purpose like for encrypting
 *         the password in the code,creating multiple environment variables.
 *         </p>
 */
@Configuration
@Component
public class ToDoConfig {
	public static final Logger logger = LoggerFactory.getLogger(ToDoConfig.class);

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
	 * 
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * This method is creating a bean for our class ObjectMapping
	 * 
	 * @return
	 */
	@Bean
	public ObjectMapping objectmapping() {
		return new ObjectMapping();
	}

	/**
	 * For getting the bean of property congfigurer
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		Resource resource;
		String activeProfile;

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

		/**
		 * For getting the active profile
		 */
		activeProfile = System.getProperty("spring.profiles.active");

		/**
		 * choose different property files for different active profile
		 */
		if ("development".equals(activeProfile)) {
			resource = new ClassPathResource("/META_INF/development.properties");
			logger.info(activeProfile + " profile selected");
		}

		else {
			resource = new ClassPathResource("/META_INF/production.properties");
			logger.info(activeProfile + " profile selected");
		}

		/**
		 * load the property file
		 */
		propertySourcesPlaceholderConfigurer.setLocation(resource);

		return propertySourcesPlaceholderConfigurer;
	}
}
