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

import com.usc.foodordering.model.Restaurant;
import com.usc.foodordering.repository.RestaurantRedisRepository;
import com.usc.foodordering.repository.RestaurantRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	RestaurantRepository restaurantRepository;
	
	@MockBean
	RestaurantRedisRepository restaurantRedisRepository;
	
	@Test
	public void testGetAllRestaurantNamesRedis() throws Exception {
		Map<Long, Restaurant> redisMap = new HashMap<>();
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setId(1l);
		restaurant1.setName("Rest1");
		redisMap.put(1l, restaurant1);
		Restaurant restaurant2 = new Restaurant();
		restaurant2.setId(2l);
		restaurant2.setName("Rest2");
		redisMap.put(2l, restaurant2);
		
		Mockito.when(restaurantRedisRepository.findAll()).thenReturn(redisMap);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurants").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{id: 1,name: Rest1},{id: 2,name: Rest2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetAllManuNamesDB() throws Exception {
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setId(1l);
		restaurant1.setName("Rest1");
		Restaurant restaurant2 = new Restaurant();
		restaurant2.setId(2l);
		restaurant2.setName("Rest2");
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant1);
		restaurants.add(restaurant2);
		
		Mockito.when(restaurantRedisRepository.findAll()).thenReturn(new HashMap<Long, Restaurant>());
		Mockito.when(restaurantRepository.findAll()).thenReturn(restaurants);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurants").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{id: 1,name: Rest1},{id: 2,name: Rest2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testCreateRestaurantName() throws Exception {
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setId(1l);
		restaurant1.setName("Rest1");
		
		String restaurantJSON = "{\"name\": \"Rest1\"}";
		String expected = "{id: 1,name: Rest1}";
		
		Mockito.when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(restaurant1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/restaurant").accept(MediaType.APPLICATION_JSON).content(restaurantJSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetRestaurantNameByIdRedis() throws Exception {
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setId(1l);
		restaurant1.setName("Rest1");
		
		Mockito.when(restaurantRedisRepository.findById(Mockito.anyLong())).thenReturn(restaurant1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurant/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id: 1,name: Rest1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetRestaurantNameByIdDB() throws Exception {
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setId(1l);
		restaurant1.setName("Rest1");
		
		Mockito.when(restaurantRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurant1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurant/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id: 1,name: Rest1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testDeleteRestaurantNameRedis() throws Exception {
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setId(1l);
		restaurant1.setName("Rest1");
		
		Mockito.when(restaurantRedisRepository.findById(Mockito.anyLong())).thenReturn(restaurant1);
		Mockito.when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurant1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/restaurant/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteRestaurantNameDB() throws Exception {
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setId(1l);
		restaurant1.setName("Rest1");
		
		Mockito.when(restaurantRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurant1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/restaurant/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
}