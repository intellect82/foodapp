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

import com.usc.foodordering.model.MenuItem;
import com.usc.foodordering.repository.MenuItemRedisRepository;
import com.usc.foodordering.repository.MenuItemRepository;

import javassist.NotFoundException;

/**
 * @author venkateswarlusayana
 * Great Learning - MenuItem MS Service 
 *
 */
@RestController
@RequestMapping("/api")
public class MenuItemController {

	@Autowired
	MenuItemRepository menuItemRepository;
	
	@Autowired
	MenuItemRedisRepository menuItemRedisRepository;

	@GetMapping("/menuitems")
	public List<MenuItem> getAllMenuItems() {
		List<MenuItem> menuItems = new ArrayList<MenuItem>(menuItemRedisRepository.findAll().values());
		if(menuItems.isEmpty()){
			menuItems = menuItemRepository.findAll();
			for(MenuItem menuItem : menuItems){
				menuItemRedisRepository.save(menuItem);
			}
		}
		return menuItems;
	}

	@PostMapping("/menuitem")
	public MenuItem createMenuItem(@Valid @RequestBody MenuItem menuItem) {
		MenuItem menuItem2 = menuItemRepository.save(menuItem);
		menuItemRedisRepository.save(menuItem2);
		return menuItem2;
	}

	@GetMapping("/menuitem/{id}")
	public MenuItem getMenuItemById(@PathVariable(value = "id") Long menuItemId) throws NotFoundException {
		MenuItem menuItem = menuItemRedisRepository.findById(menuItemId);
		if(null == menuItem){
			menuItem = menuItemRepository.findById(menuItemId)
					.orElseThrow(() -> new NotFoundException("MenuItem with id = " + menuItemId + " Not found!")); 
			menuItemRedisRepository.save(menuItem);
		}
		return menuItem;
	}

	// TODO Put Method

	@DeleteMapping("/menuitem/{id}")
	public ResponseEntity<?> deleteMenuItem(@PathVariable(value = "id") Long menuItemId) throws NotFoundException {
		MenuItem menuItem = menuItemRedisRepository.findById(menuItemId);
		if(null!=menuItem){
			menuItemRedisRepository.delete(menuItemId);
		}
		menuItem = menuItemRepository.findById(menuItemId)
				.orElseThrow(() -> new NotFoundException("MenuItem with id = " + menuItemId + " Not found!"));
		menuItemRepository.delete(menuItem);
		return ResponseEntity.ok().build();
	}
}
