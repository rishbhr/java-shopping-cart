package com.reviewjava.shoppingcart.service.product;

import com.reviewjava.shoppingcart.exceptions.ProductNotFoundException;
import com.reviewjava.shoppingcart.model.Category;
import com.reviewjava.shoppingcart.model.Product;
import com.reviewjava.shoppingcart.repository.CategoryRespository;
import com.reviewjava.shoppingcart.repository.ProductRepository;
import com.reviewjava.shoppingcart.request.AddProductRequest;
import com.reviewjava.shoppingcart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRespository categoryRespository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if category is found in the database
        // if yes, set it as the product category
        // if no, save it as a new category and set as product category
        Category category = Optional.ofNullable(categoryRespository.findByName(request.getCategory().getName()))
                .orElseGet(() -> categoryRespository.save(request.getCategory()));
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return  productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);

    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRespository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
