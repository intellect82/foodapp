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

import com.usc.foodordering.model.RestaurantMenu;
import com.usc.foodordering.repository.RestaurantMenuRedisRepository;
import com.usc.foodordering.repository.RestaurantMenuRepository;

import javassist.NotFoundException;

/**
 * @author venkateswarlusayana
 * Great Learning - RestaurantMenu MS Service 
 *
 */
@RestController
@RequestMapping("/api")
public class RestaurantMenuController {

	@Autowired
	RestaurantMenuRepository restaurantMenuRepository;
	
	@Autowired
	RestaurantMenuRedisRepository restaurantMenuRedisRepository;

	@GetMapping("/restaurantmenus")
	public List<RestaurantMenu> getAllRestaurantMenus() {
		List<RestaurantMenu> restaurantMenus = new ArrayList<RestaurantMenu>(restaurantMenuRedisRepository.findAll().values());
		if(restaurantMenus.isEmpty()){
			restaurantMenus = restaurantMenuRepository.findAll();
			for(RestaurantMenu restaurantMenu : restaurantMenus){
				restaurantMenuRedisRepository.save(restaurantMenu);
			}
		}
		return restaurantMenus;
	}

	@PostMapping("/restaurantmenu")
	public RestaurantMenu createRestaurantMenu(@Valid @RequestBody RestaurantMenu restaurantMenu) {
		RestaurantMenu restaurantMenu2 = restaurantMenuRepository.save(restaurantMenu);
		restaurantMenuRedisRepository.save(restaurantMenu2);
		return restaurantMenu2;
	}

	@GetMapping("/restaurantmenu/{id}")
	public RestaurantMenu getRestaurantMenuById(@PathVariable(value = "id") Long restaurantMenuId) throws NotFoundException {
		RestaurantMenu restaurantMenu = restaurantMenuRedisRepository.findById(restaurantMenuId);
		if(null == restaurantMenu){
			restaurantMenu = restaurantMenuRepository.findById(restaurantMenuId)
					.orElseThrow(() -> new NotFoundException("RestaurantMenu with id = " + restaurantMenuId + " Not found!")); 
			restaurantMenuRedisRepository.save(restaurantMenu);
		}
		return restaurantMenu;
	}

	// TODO Put Method

	@DeleteMapping("/restaurantmenu/{id}")
	public ResponseEntity<?> deleteRestaurantMenu(@PathVariable(value = "id") Long restaurantMenuId) throws NotFoundException {
		RestaurantMenu restaurantMenu = restaurantMenuRedisRepository.findById(restaurantMenuId);
		if(null!=restaurantMenu){
			restaurantMenuRedisRepository.delete(restaurantMenuId);
		}
		restaurantMenu = restaurantMenuRepository.findById(restaurantMenuId)
				.orElseThrow(() -> new NotFoundException("RestaurantMenu with id = " + restaurantMenuId + " Not found!"));
		restaurantMenuRepository.delete(restaurantMenu);
		return ResponseEntity.ok().build();
	}
}
