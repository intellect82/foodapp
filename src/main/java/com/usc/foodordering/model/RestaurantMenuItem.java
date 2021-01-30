package com.usc.foodordering.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@IdClass(RestaurantMenuItemId.class)
@Table(name = "restaurant_menu_item")
@EntityListeners(AuditingEntityListener.class)
public class RestaurantMenuItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private Long restaurantId;

	@Id
	@NotNull
	private Integer menuId;
	
	@Id
	@NotNull
	private Long menuItemId;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	public Long getMenuItemId() {
		return menuItemId;
	}
	
	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}
}
