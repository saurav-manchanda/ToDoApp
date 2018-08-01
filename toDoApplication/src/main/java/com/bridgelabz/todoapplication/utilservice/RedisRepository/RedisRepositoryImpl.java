/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating an Redis Repository implementation class. The redis Repository is being used for storing
 * tokens. We are getting the respective tokens and then comparing the respective email with 
 * the existing token userID.If they both are not equal then we are throwing the error.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 22/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice.RedisRepository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;

/**
 * @author Saurav
 *         <p>
 *         This class is Redis Repository Implementation. The redis Repository
 *         is being used for storing tokens. We are getting the respective
 *         tokens and then comparing the respective userId with the existing
 *         token email.If they both are not equal then we are throwing the
 *         error.
 *         </p>
 */
@org.springframework.stereotype.Repository
public class RedisRepositoryImpl implements IRedisRepository {
	private static final String KEY = "ToDoApplicationToken";
	private RedisTemplate<String, User> redisTemplate;
	private HashOperations<String, String, String> hashOperations;

	@Autowired
	TokenGenerator tokenGenerator;

	@Autowired
	Repository userRepository;

	/**
	 * Parameterized Construction
	 * 
	 * @param redisTemplate
	 */
	@Autowired
	public RedisRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * Constructor
	 */
	public RedisRepositoryImpl() {
	}

	/**
	 * Method to initialize the hashOperations
	 */
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * This method is for setting up the Token in the redis repository
	 */
	@Override
	public void setToken(String token) {
		String email = tokenGenerator.parseJWT(token);
		String userId= userRepository.getByEmail(email).get().getId();
		hashOperations.put(KEY, userId, token);
	}

	/**
	 * This method is for getting the token from the redis repository with the key
	 * we have passed
	 */
	@Override
	public String getToken(String userId) {
		return hashOperations.get(KEY, userId);
	}

	/**
	 * this method is for deleting the token from the Redis repository with the
	 * respective key we have passed
	 */
	@Override
	public void deleteToken(String userId) {
		hashOperations.delete(KEY, userId);
	}
}
