package com.usc.foodordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usc.foodordering.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>{

}
