package com.bridgelabz.todoapplication.utilservice.RedisRepository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;

@org.springframework.stereotype.Repository
public class RedisRepositoryImpl implements IRedisRepository {
	private static final String KEY = "ToDoApplicationToken";
	private RedisTemplate<String, User> redisTemplate;
	private HashOperations<String, String, String> hashOperations;

	@Autowired
	TokenGenerator tokenGenerator;
	
	@Autowired
	Repository userRepository;

	@Autowired
	public RedisRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
			this.redisTemplate = redisTemplate;
	}
	public RedisRepositoryImpl() {
	}
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void setToken(String token) {
		String email = tokenGenerator.parseJWT(token);
		hashOperations.put(KEY, email, token);
	}

	@Override
	public String getToken(String email) {
		return hashOperations.get(KEY, email);
	}

	@Override
	public void deleteToken(String email) {
		hashOperations.delete(KEY, email);
	}
}
