package com.usc.foodordering.repository;

import java.util.Map;

import com.usc.foodordering.model.Menu;

public interface MenuRedisRepository {
	Map<Integer, Menu> findAll();
	void save(Menu menu);
	void delete(Integer id);
	Menu findById(Integer id);
}
