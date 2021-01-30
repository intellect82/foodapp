package com.usc.foodordering.repository;

import java.util.Map;

import com.usc.foodordering.model.Restaurant;

public interface RestaurantRedisRepository {
	Map<Long, Restaurant> findAll();
	void save(Restaurant restaurant);
	void delete(Long id);
	Restaurant findById(Long id);
}
