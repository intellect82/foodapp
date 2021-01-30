package com.usc.foodordering.repository.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.RestaurantMenuItem;
import com.usc.foodordering.repository.RestaurantMenuItemRedisRepository;

@Repository
public class RestaurantMenuItemRedisRepositoryImpl implements RestaurantMenuItemRedisRepository{

	private static final String KEY = "RestaurantMenuItem";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, Long, RestaurantMenuItem> hashOperations;
	
	@Autowired
	public RestaurantMenuItemRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public Map<Long, RestaurantMenuItem> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public void save(RestaurantMenuItem restaurantMenuItem) {
		hashOperations.put(KEY, restaurantMenuItem.getRestaurantId(), restaurantMenuItem);
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete(KEY, id);
	}

	@Override
	public RestaurantMenuItem findById(Long id) {
		return hashOperations.get(KEY, id);
	}
	
}
