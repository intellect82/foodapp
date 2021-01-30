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

import com.usc.foodordering.model.RestaurantMenuItem;
import com.usc.foodordering.repository.RestaurantMenuItemRedisRepository;
import com.usc.foodordering.repository.RestaurantMenuItemRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantMenuItemController.class)
public class RestaurantMenuItemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	RestaurantMenuItemRepository restaurantMenuItemRepository;
	
	@MockBean
	RestaurantMenuItemRedisRepository restaurantMenuItemRedisRepository;
	
	@Test
	public void testGetAllRestaurantMenuItemNamesRedis() throws Exception {
		Map<Long, RestaurantMenuItem> redisMap = new HashMap<>();
		RestaurantMenuItem restaurantMenuItem1 = new RestaurantMenuItem();
		restaurantMenuItem1.setRestaurantId(1l);
		restaurantMenuItem1.setMenuId(1);
		restaurantMenuItem1.setMenuItemId(1l);
		redisMap.put(1l, restaurantMenuItem1);
		RestaurantMenuItem restaurantMenuItem2 = new RestaurantMenuItem();
		restaurantMenuItem2.setRestaurantId(2l);
		restaurantMenuItem2.setMenuId(2);
		restaurantMenuItem2.setMenuItemId(2l);
		redisMap.put(2l, restaurantMenuItem2);
		
		Mockito.when(restaurantMenuItemRedisRepository.findAll()).thenReturn(redisMap);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenuitems").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{restaurantId: 1,menuId: 1,menuItemId: 1},{restaurantId: 2,menuId: 2,menuItemId: 2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetAllRestaurantMenuItemsDB() throws Exception {
		RestaurantMenuItem restaurantMenuItem1 = new RestaurantMenuItem();
		restaurantMenuItem1.setRestaurantId(1l);
		restaurantMenuItem1.setMenuId(1);
		restaurantMenuItem1.setMenuItemId(1l);
		RestaurantMenuItem restaurantMenuItem2 = new RestaurantMenuItem();
		restaurantMenuItem2.setRestaurantId(2l);
		restaurantMenuItem2.setMenuId(2);
		restaurantMenuItem2.setMenuItemId(2l);
		List<RestaurantMenuItem> restaurantMenuItems = new ArrayList<>();
		restaurantMenuItems.add(restaurantMenuItem1);
		restaurantMenuItems.add(restaurantMenuItem2);
		
		Mockito.when(restaurantMenuItemRedisRepository.findAll()).thenReturn(new HashMap<Long, RestaurantMenuItem>());
		Mockito.when(restaurantMenuItemRepository.findAll()).thenReturn(restaurantMenuItems);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenuitems").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{restaurantId: 1,menuId: 1,menuItemId: 1},{restaurantId: 2,menuId: 2,menuItemId: 2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testCreateRestaurantMenuItemName() throws Exception {
		RestaurantMenuItem restaurantMenuItem1 = new RestaurantMenuItem();
		restaurantMenuItem1.setRestaurantId(1l);
		restaurantMenuItem1.setMenuId(1);
		restaurantMenuItem1.setMenuItemId(1l);
		
		String restaurantMenuItemJSON = "{\"restaurantId\": \"1\",\"menuId\": \"1\",\"menuItemId\": \"1\"}";
		String expected = "{restaurantId: 1,menuId: 1,menuItemId: 1}";
		
		Mockito.when(restaurantMenuItemRepository.save(Mockito.any(RestaurantMenuItem.class))).thenReturn(restaurantMenuItem1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/restaurantmenuitem").accept(MediaType.APPLICATION_JSON).content(restaurantMenuItemJSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetRestaurantMenuItemNameByIdRedis() throws Exception {
		RestaurantMenuItem restaurantMenuItem1 = new RestaurantMenuItem();
		restaurantMenuItem1.setRestaurantId(1l);
		restaurantMenuItem1.setMenuId(1);
		restaurantMenuItem1.setMenuItemId(1l);
		
		Mockito.when(restaurantMenuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(restaurantMenuItem1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{restaurantId: 1,menuId: 1,menuItemId: 1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetRestaurantMenuItemNameByIdDB() throws Exception {
		RestaurantMenuItem restaurantMenuItem1 = new RestaurantMenuItem();
		restaurantMenuItem1.setRestaurantId(1l);
		restaurantMenuItem1.setMenuId(1);
		restaurantMenuItem1.setMenuItemId(1l);
		
		Mockito.when(restaurantMenuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(restaurantMenuItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurantMenuItem1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantmenuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{restaurantId: 1,menuId: 1,menuItemId: 1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testDeleteRestaurantMenuItemNameRedis() throws Exception {
		RestaurantMenuItem restaurantMenuItem1 = new RestaurantMenuItem();
		restaurantMenuItem1.setRestaurantId(1l);
		restaurantMenuItem1.setMenuId(1);
		restaurantMenuItem1.setMenuItemId(1l);
		
		Mockito.when(restaurantMenuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(restaurantMenuItem1);
		Mockito.when(restaurantMenuItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurantMenuItem1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/restaurantmenuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteRestaurantMenuItemNameDB() throws Exception {
		RestaurantMenuItem restaurantMenuItem1 = new RestaurantMenuItem();
		restaurantMenuItem1.setRestaurantId(1l);
		restaurantMenuItem1.setMenuId(1);
		restaurantMenuItem1.setMenuItemId(1l);
		
		Mockito.when(restaurantMenuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(restaurantMenuItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurantMenuItem1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/restaurantmenuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
}