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

import com.usc.foodordering.model.Menu;
import com.usc.foodordering.repository.MenuRedisRepository;
import com.usc.foodordering.repository.MenuRepository;

import javassist.NotFoundException;

/**
 * @author venkateswarlusayana
 * Great Learning - Menu MS Service 
 *
 */
@RestController
@RequestMapping("/api")
public class MenuController {
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuRedisRepository menuRedisRepository;
	
	@GetMapping("/menutypes")
	public List<Menu> getAllManuTypes(){
		List<Menu> menutypes = new ArrayList<Menu>(menuRedisRepository.findAll().values());
		if(menutypes.isEmpty()){
			menutypes = menuRepository.findAll();
			for(Menu menutype: menutypes){
				menuRedisRepository.save(menutype);
			}
		}
		return menutypes;
	}
	
	@PostMapping("/menutype")
	public Menu createMenuType(@Valid @RequestBody Menu menu) {
		Menu menu2 = menuRepository.save(menu);
		menuRedisRepository.save(menu2);
		return menu2;
	}
	
	@GetMapping("/menutype/{id}")
	public Menu getMenuTypeById(@PathVariable(value = "id") Integer menuId) throws NotFoundException {
		Menu menu = menuRedisRepository.findById(menuId);
		if(null==menu){
			menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu with id = " + menuId + " Not found."));
			menuRedisRepository.save(menu);
		}
		return menu;
	}
	
	@DeleteMapping("/menutype/{id}")
	public ResponseEntity<?> deleteMenuType(@PathVariable(value = "id") Integer menuId) throws NotFoundException {
		Menu menu = menuRedisRepository.findById(menuId);
		if(null!=menu){
			menuRedisRepository.delete(menuId);
		}
		menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu with id = " + menuId + " Not found!"));
		menuRepository.delete(menu);
		return ResponseEntity.ok().build();
	}
}
