package com.usc.foodordering.repository;

import java.util.Map;

import com.usc.foodordering.model.MenuItem;

public interface MenuItemRedisRepository {
	Map<Long, MenuItem> findAll();
	void save(MenuItem menuItem);
	void delete(Long id);
	MenuItem findById(Long id);
}
