package com.usc.foodordering.repository.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.MenuItem;
import com.usc.foodordering.repository.MenuItemRedisRepository;

@Repository
public class MenuItemRedisRepositoryImpl implements MenuItemRedisRepository{

	private static final String KEY = "MenuItem";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, Long, MenuItem> hashOperations;
	
	@Autowired
	public MenuItemRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public Map<Long, MenuItem> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public void save(MenuItem menuItem) {
		hashOperations.put(KEY, menuItem.getId(), menuItem);
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete(KEY, id);
	}

	@Override
	public MenuItem findById(Long id) {
		return hashOperations.get(KEY, id);
	}
	
}