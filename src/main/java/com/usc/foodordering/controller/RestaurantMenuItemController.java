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

import com.usc.foodordering.model.RestaurantMenuItem;
import com.usc.foodordering.repository.RestaurantMenuItemRedisRepository;
import com.usc.foodordering.repository.RestaurantMenuItemRepository;

import javassist.NotFoundException;

/**
 * @author venkateswarlusayana
 * Great Learning - RestaurantMEnuItem  MS Service 
 *
 */
@RestController
@RequestMapping("/api")
public class RestaurantMenuItemController {

	@Autowired
	RestaurantMenuItemRepository restaurantMenuItemRepository;
	
	@Autowired
	RestaurantMenuItemRedisRepository restaurantMenuItemRedisRepository;

	@GetMapping("/restaurantmenuitems")
	public List<RestaurantMenuItem> getAllRestaurantMenuItems() {
		List<RestaurantMenuItem> restaurantMenuItems = new ArrayList<RestaurantMenuItem>(restaurantMenuItemRedisRepository.findAll().values());
		if(restaurantMenuItems.isEmpty()){
			restaurantMenuItems = restaurantMenuItemRepository.findAll();
			for(RestaurantMenuItem restaurantMenuItem : restaurantMenuItems){
				restaurantMenuItemRedisRepository.save(restaurantMenuItem);
			}
		}
		return restaurantMenuItems;
	}

	@PostMapping("/restaurantmenuitem")
	public RestaurantMenuItem createRestaurantMenuItem(@Valid @RequestBody RestaurantMenuItem restaurantMenuItem) {
		RestaurantMenuItem restaurantMenuItem2 = restaurantMenuItemRepository.save(restaurantMenuItem);
		restaurantMenuItemRedisRepository.save(restaurantMenuItem2);
		return restaurantMenuItem2;
	}

	@GetMapping("/restaurantmenuitem/{id}")
	public RestaurantMenuItem getRestaurantMenuItemById(@PathVariable(value = "id") Long restaurantMenuItemId) throws NotFoundException {
		RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRedisRepository.findById(restaurantMenuItemId);
		if(null == restaurantMenuItem){
			restaurantMenuItem = restaurantMenuItemRepository.findById(restaurantMenuItemId)
					.orElseThrow(() -> new NotFoundException("RestaurantMenuItem with id = " + restaurantMenuItemId + " Not found!")); 
			restaurantMenuItemRedisRepository.save(restaurantMenuItem);
		}
		return restaurantMenuItem;
	}

	// TODO Put Method

	@DeleteMapping("/restaurantmenuitem/{id}")
	public ResponseEntity<?> deleteRestaurantMenuItem(@PathVariable(value = "id") Long restaurantMenuItemId) throws NotFoundException {
		RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRedisRepository.findById(restaurantMenuItemId);
		if(null!=restaurantMenuItem){
			restaurantMenuItemRedisRepository.delete(restaurantMenuItemId);
		}
		restaurantMenuItem = restaurantMenuItemRepository.findById(restaurantMenuItemId)
				.orElseThrow(() -> new NotFoundException("RestaurantMenuItem with id = " + restaurantMenuItemId + " Not found!"));
		restaurantMenuItemRepository.delete(restaurantMenuItem);
		return ResponseEntity.ok().build();
	}
}
