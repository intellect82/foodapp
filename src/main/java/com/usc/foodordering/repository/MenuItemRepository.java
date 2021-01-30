package com.usc.foodordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{

}
