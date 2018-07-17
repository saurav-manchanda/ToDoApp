/********************************************************************************* *
 * Purpose: To do Login Registration with the help of MONGODB repository. 
 * Creating a class which is for the congiguring the swagger into picture
 * 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 16/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Saurav
 *         <p>
 *         This class is for the congiguring the swagger into picture
 *         </p>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * @return Docket object
	 *         <p>
	 *         This method configures the Swagger with the path selector given
	 *         </p>
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}
