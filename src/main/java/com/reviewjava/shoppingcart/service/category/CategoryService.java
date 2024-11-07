package com.reviewjava.shoppingcart.service.category;

import com.reviewjava.shoppingcart.exceptions.AlreadyExistsException;
import com.reviewjava.shoppingcart.exceptions.ResourceNotFoundException;
import com.reviewjava.shoppingcart.model.Category;
import com.reviewjava.shoppingcart.repository.CategoryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRespository categoryRespository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRespository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRespository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(cat -> !categoryRespository.existsByName(cat.getName()))
                .map(categoryRespository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(categoryRespository.getById(id)).map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRespository.save(oldCategory);
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRespository.findById(id)
                .ifPresentOrElse(categoryRespository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found");
                });
    }
}
