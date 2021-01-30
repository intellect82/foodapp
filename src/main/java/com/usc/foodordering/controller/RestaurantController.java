package com.usc.foodordering.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usc.foodordering.model.Restaurant;
import com.usc.foodordering.repository.RestaurantRedisRepository;
import com.usc.foodordering.repository.RestaurantRepository;

import javassist.NotFoundException;

/**
 * @author venkateswarlusayana
 * Great Learning - Restaurant MS Service 
 *
 */
@RestController
@RequestMapping("/api")
public class RestaurantController {

	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	RestaurantRedisRepository restaurantRedisRepository;

	@GetMapping("/restaurants")
	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = new ArrayList<Restaurant>(restaurantRedisRepository.findAll().values());
		if(restaurants.isEmpty()){
			restaurants = restaurantRepository.findAll();
			for(Restaurant restaurant : restaurants){
				restaurantRedisRepository.save(restaurant);
			}
		}
		return restaurants;
	}

	@PostMapping("/restaurant")
	public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
		Restaurant restaurant2 = restaurantRepository.save(restaurant);
		restaurantRedisRepository.save(restaurant2);
		return restaurant2;
	}

	@GetMapping("/restaurant/{id}")
	public Restaurant getRestaurantById(@PathVariable(value = "id") Long restaurantId) throws NotFoundException {
		Restaurant restaurant = restaurantRedisRepository.findById(restaurantId);
		if(null == restaurant){
			restaurant = restaurantRepository.findById(restaurantId)
					.orElseThrow(() -> new NotFoundException("Restaurant with id = " + restaurantId + " Not found!")); 
			restaurantRedisRepository.save(restaurant);
		}
		return restaurant;
	}

	// TODO Put Method

	@DeleteMapping("/restaurant/{id}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable(value = "id") Long restaurantId) throws NotFoundException {
		Restaurant restaurant = restaurantRedisRepository.findById(restaurantId);
		if(null!=restaurant){
			restaurantRedisRepository.delete(restaurantId);
		}
		restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFoundException("Restaurant with id = " + restaurantId + " Not found!"));
		restaurantRepository.delete(restaurant);
		return ResponseEntity.ok().build();
	}
}
