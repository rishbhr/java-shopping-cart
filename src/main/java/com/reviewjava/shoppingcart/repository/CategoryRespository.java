package com.reviewjava.shoppingcart.repository;

import com.reviewjava.shoppingcart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
