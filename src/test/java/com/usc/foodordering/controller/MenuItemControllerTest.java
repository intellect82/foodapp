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

import com.usc.foodordering.model.MenuItem;
import com.usc.foodordering.repository.MenuItemRedisRepository;
import com.usc.foodordering.repository.MenuItemRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	MenuItemRepository menuItemRepository;
	
	@MockBean
	MenuItemRedisRepository menuItemRedisRepository;
	
	@Test
	public void testGetAllMenuItemNamesRedis() throws Exception {
		Map<Long, MenuItem> redisMap = new HashMap<>();
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1l);
		menuItem1.setName("item1");
		redisMap.put(1l, menuItem1);
		MenuItem menuItem2 = new MenuItem();
		menuItem2.setId(2l);
		menuItem2.setName("item2");
		redisMap.put(2l, menuItem2);
		
		Mockito.when(menuItemRedisRepository.findAll()).thenReturn(redisMap);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menuitems").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{id: 1,name: item1},{id: 2,name: item2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetAllMenuItemsDB() throws Exception {
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1l);
		menuItem1.setName("item1");
		MenuItem menuItem2 = new MenuItem();
		menuItem2.setId(2l);
		menuItem2.setName("item2");
		List<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(menuItem1);
		menuItems.add(menuItem2);
		
		Mockito.when(menuItemRedisRepository.findAll()).thenReturn(new HashMap<Long, MenuItem>());
		Mockito.when(menuItemRepository.findAll()).thenReturn(menuItems);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menuitems").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{id: 1,name: item1},{id: 2,name: item2}]";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testCreateMenuItemName() throws Exception {
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1l);
		menuItem1.setName("item1");
		
		String menuItemJSON = "{\"name\": \"item1\"}";
		String expected = "{id: 1,name: item1}";
		
		Mockito.when(menuItemRepository.save(Mockito.any(MenuItem.class))).thenReturn(menuItem1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/menuitem").accept(MediaType.APPLICATION_JSON).content(menuItemJSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetMenuItemNameByIdRedis() throws Exception {
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1l);
		menuItem1.setName("item1");
		
		Mockito.when(menuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(menuItem1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id: 1,name: item1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetMenuItemNameByIdDB() throws Exception {
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1l);
		menuItem1.setName("item1");
		
		Mockito.when(menuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(menuItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(menuItem1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/menuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id: 1,name: item1}";
		
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testDeleteMenuItemNameRedis() throws Exception {
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1l);
		menuItem1.setName("item1");
		
		Mockito.when(menuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(menuItem1);
		Mockito.when(menuItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(menuItem1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/menuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteMenuItemNameDB() throws Exception {
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1l);
		menuItem1.setName("item1");
		
		Mockito.when(menuItemRedisRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(menuItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(menuItem1));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/menuitem/1").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
}