package com.usc.foodordering.repository.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.Menu;
import com.usc.foodordering.repository.MenuRedisRepository;

@Repository
public class MenuRedisRepositoryImpl implements MenuRedisRepository{

	private static final String KEY = "Menu";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, Integer, Menu> hashOperations;
	
	@Autowired
	public MenuRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public Map<Integer, Menu> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public void save(Menu menu) {
		hashOperations.put(KEY, menu.getId(), menu);
	}

	@Override
	public void delete(Integer id) {
		hashOperations.delete(KEY, id);
	}

	@Override
	public Menu findById(Integer id) {
		return hashOperations.get(KEY, id);
	}
	
}