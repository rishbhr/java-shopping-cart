package com.reviewjava.shoppingcart.repository;

import com.reviewjava.shoppingcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
