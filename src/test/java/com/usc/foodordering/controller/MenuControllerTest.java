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

import com.usc.foodordering.model.Menu;
import com.usc.foodordering.repository.MenuRedisRepository;
import com.usc.foodordering.repository.MenuRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuController.class)
public class MenuControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	MenuRepository menuRepository;
	
	@MockBean
	MenuRedisRepository menuRedisRepository;
	
	@Test
	public void testGetAllMenuTypesRedis() throws Exception {
		Map<Integer, Menu> redisMap = new HashMap<>();
		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setType("Breakfast");
		redisMap.put(1, menu1);
		Menu menu2 = new Menu();
		menu2.setId(2);
		menu2.setType("Lunch");
		redisMap.put(2, menu2);
		
		Mockito.when(menuRedisRepository.findAll()).thenReturn(redisMap);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menutypes").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{id: 1,type: Breakfast},{id: 2,type: Lunch}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetAllManuTypesDB() throws Exception {
		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setType("Breakfast");
		Menu menu2 = new Menu();
		menu2.setId(2);
		menu2.setType("Lunch");
		List<Menu> menutypes = new ArrayList<>();
		menutypes.add(menu1);
		menutypes.add(menu2);
		
		Mockito.when(menuRedisRepository.findAll()).thenReturn(new HashMap<Integer, Menu>());
		Mockito.when(menuRepository.findAll()).thenReturn(menutypes);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menutypes").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{id: 1,type: Breakfast},{id: 2,type: Lunch}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testCreateMenuType() throws Exception {
		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setType("Breakfast");
		
		String menuJSON = "{\"type\": \"Breakfast\"}";
		String expected = "{id: 1, type: Breakfast}";
		
		Mockito.when(menuRepository.save(Mockito.any(Menu.class))).thenReturn(menu1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/menutype").accept(MediaType.APPLICATION_JSON).content(menuJSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetMenuTypeByIdRedis() throws Exception {
		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setType("Breakfast");
		
		Mockito.when(menuRedisRepository.findById(Mockito.anyInt())).thenReturn(menu1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menutype/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id: 1,type: Breakfast}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetMenuTypeByIdDB() throws Exception {
		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setType("Breakfast");
		
		Mockito.when(menuRedisRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(menuRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(menu1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menutype/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id: 1,type: Breakfast}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testDeleteMenuTypeRedis() throws Exception {
		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setType("Breakfast");
		
		Mockito.when(menuRedisRepository.findById(Mockito.anyInt())).thenReturn(menu1);
		Mockito.when(menuRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(menu1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/menutype/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteMenuTypeDB() throws Exception {
		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setType("Breakfast");
		
		Mockito.when(menuRedisRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(menuRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(menu1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/menutype/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
}
