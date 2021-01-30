package com.usc.foodordering.repository;

import java.util.Map;

import com.usc.foodordering.model.RestaurantMenu;

public interface RestaurantMenuRedisRepository {
	Map<Long, RestaurantMenu> findAll();
	void save(RestaurantMenu restaurantMenu);
	void delete(Long id);
	RestaurantMenu findById(Long id);
}
