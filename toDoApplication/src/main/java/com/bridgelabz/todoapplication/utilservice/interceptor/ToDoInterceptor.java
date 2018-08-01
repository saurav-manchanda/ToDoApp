/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication). This application is basically storing and maintaining the notes.
 * Creating an interceptor named ToDoInterceptor
 * @author Saurav Manchanda
 * @version 1.0
 * @since 30/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;
import com.bridgelabz.todoapplication.utilservice.RedisRepository.IRedisRepository;

/**
 * @author Saurav
 *         <p>
 *         This class is ToDoiInterceptor in which parsing of token is done as
 *         well as we are checking the token from redis repository.
 *         </p>
 */
@Component
public class ToDoInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	TokenGenerator tokenGenerator;
	@Autowired
	IRedisRepository redisRepository;
	@Autowired
	Repository userRepository;
	public static final Logger logger = LoggerFactory.getLogger(ToDoInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		logger.info("ToDoInterceptor called");
		String token = request.getHeader("token");
		String email = tokenGenerator.parseJWT(token);
		String userId = userRepository.getByEmail(email).get().getId();
		String tokenfromredis = redisRepository.getToken(userId);

		if (tokenfromredis != null) {
			request.setAttribute("userId", userId);
			return true;
		}
		return false;
	}
}
