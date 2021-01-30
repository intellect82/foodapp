package com.usc.foodordering.repository;

import java.util.Map;

import com.usc.foodordering.model.RestaurantMenuItem;

public interface RestaurantMenuItemRedisRepository {
	Map<Long, RestaurantMenuItem> findAll();
	void save(RestaurantMenuItem restaurantMenuItem);
	void delete(Long id);
	RestaurantMenuItem findById(Long id);
}
