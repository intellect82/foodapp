package com.usc.foodordering.repository.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.RestaurantMenu;
import com.usc.foodordering.repository.RestaurantMenuRedisRepository;

@Repository
public class RestaurantMenuRedisRepositoryImpl implements RestaurantMenuRedisRepository{

	private static final String KEY = "RestaurantMenu";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, Long, RestaurantMenu> hashOperations;
	
	@Autowired
	public RestaurantMenuRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public Map<Long, RestaurantMenu> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public void save(RestaurantMenu restaurantMenu) {
		hashOperations.put(KEY, restaurantMenu.getRestaurantId(), restaurantMenu);
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete(KEY, id);
	}

	@Override
	public RestaurantMenu findById(Long id) {
		return hashOperations.get(KEY, id);
	}
	
}
