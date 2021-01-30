package com.usc.foodordering.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.usc.foodordering.model.RestaurantMenu;
import com.usc.foodordering.repository.RestaurantMenuRedisRepository;
import com.usc.foodordering.repository.RestaurantMenuRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantMenuController.class)
public class RestaurantMenuControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	RestaurantMenuRepository restaurantMenuRepository;
	
	@MockBean
	RestaurantMenuRedisRepository restaurantMenuRedisRepository;
	
	@Test
	public void testGetAllRestaurantMenuNamesRedis() throws Exception {
		Map<Long, RestaurantMenu> redisMap = new HashMap<>();
		RestaurantMenu restaurantMenu1 = new RestaurantMenu();
		restaurantMenu1.setRestaurantId(1l);
		restaurantMenu1.setMenuId(1);
		redisMap.put(1l, restaurantMenu1);
		RestaurantMenu restaurantMenu2 = new RestaurantMenu();
		restaurantMenu2.setRestaurantId(2l);
		restaurantMenu2.setMenuId(2);
		redisMap.put(2l, restaurantMenu2);
		
		Mockito.when(restaurantMenuRedisRepository.findAll()).thenReturn(redisMap);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenus").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{restaurantId: 1,menuId: 1},{restaurantId: 2,menuId: 2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetAllRestaurantMenusDB() throws Exception {
		RestaurantMenu restaurantMenu1 = new RestaurantMenu();
		restaurantMenu1.setRestaurantId(1l);
		restaurantMenu1.setMenuId(1);
		RestaurantMenu restaurantMenu2 = new RestaurantMenu();
		restaurantMenu2.setRestaurantId(2l);
		restaurantMenu2.setMenuId(2);
		List<RestaurantMenu> restaurantMenus = new ArrayList<>();
		restaurantMenus.add(restaurantMenu1);
		restaurantMenus.add(restaurantMenu2);
		
		Mockito.when(restaurantMenuRedisRepository.findAll()).thenReturn(new HashMap<Long, RestaurantMenu>());
		Mockito.when(restaurantMenuRepository.findAll()).thenReturn(restaurantMenus);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenus").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{restaurantId: 1,menuId: 1},{restaurantId: 2,menuId: 2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testCreateRestaurantMenuName() throws Exception {
		RestaurantMenu restaurantMenu1 = new RestaurantMenu();
		restaurantMenu1.setRestaurantId(1l);
		restaurantMenu1.setMenuId(1);
		
		String restaurantMenuJSON = "{\"restaurantId\": \"1\",\"menuId\": \"1\"}";
		String expected = "{restaurantId: 1,menuId: 1}";
		
		Mockito.when(restaurantMenuRepository.save(Mockito.any(RestaurantMenu.class))).thenReturn(restaurantMenu1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/restaurantmenu").accept(MediaType.APPLICATION_JSON).content(restaurantMenuJSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetRestaurantMenuNameByIdRedis() throws Exception {
		RestaurantMenu restaurantMenu1 = new RestaurantMenu();
		restaurantMenu1.setRestaurantId(1l);
		restaurantMenu1.setMenuId(1);
		
		Mockito.when(restaurantMenuRedisRepository.findById(Mockito.anyLong())).thenReturn(restaurantMenu1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenu/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{restaurantId: 1,menuId: 1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetRestaurantMenuNameByIdDB() throws Exception {
		RestaurantMenu restaurantMenu1 = new RestaurantMenu();
		restaurantMenu1.setRestaurantId(1l);
		restaurantMenu1.setMenuId(1);
		
		Mockito.when(restaurantMenuRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(restaurantMenuRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurantMenu1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenu/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{restaurantId: 1,menuId: 1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testDeleteRestaurantMenuNameRedis() throws Exception {
		RestaurantMenu restaurantMenu1 = new RestaurantMenu();
		restaurantMenu1.setRestaurantId(1l);
		restaurantMenu1.setMenuId(1);
		
		Mockito.when(restaurantMenuRedisRepository.findById(Mockito.anyLong())).thenReturn(restaurantMenu1);
		Mockito.when(restaurantMenuRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurantMenu1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/restaurantmenu/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteRestaurantMenuNameDB() throws Exception {
		RestaurantMenu restaurantMenu1 = new RestaurantMenu();
		restaurantMenu1.setRestaurantId(1l);
		restaurantMenu1.setMenuId(1);
		
		Mockito.when(restaurantMenuRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(restaurantMenuRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurantMenu1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/restaurantmenu/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
}