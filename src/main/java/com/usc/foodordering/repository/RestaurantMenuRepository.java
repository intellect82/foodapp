package com.usc.foodordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.RestaurantMenu;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long>{

}
