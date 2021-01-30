package com.usc.foodordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.RestaurantMenuItem;

@Repository
public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long>{

}
