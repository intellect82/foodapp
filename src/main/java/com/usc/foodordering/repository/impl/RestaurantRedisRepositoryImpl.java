package com.usc.foodordering.repository.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.Restaurant;
import com.usc.foodordering.repository.RestaurantRedisRepository;

@Repository
public class RestaurantRedisRepositoryImpl implements RestaurantRedisRepository{

	private static final String KEY = "Restaurant";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, Long, Restaurant> hashOperations;
	
	@Autowired
	public RestaurantRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public Map<Long, Restaurant> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public void save(Restaurant restaurant) {
		hashOperations.put(KEY, restaurant.getId(), restaurant);
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete(KEY, id);
	}

	@Override
	public Restaurant findById(Long id) {
		return hashOperations.get(KEY, id);
	}
	
}
