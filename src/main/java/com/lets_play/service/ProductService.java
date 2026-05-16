package com.lets_play.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lets_play.model.Product;
import com.lets_play.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        // Get the email of the currently logged-in user from the JWT
        String currentUserEmail = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        // Set the userId on the product before saving
        product.setUserId(currentUserEmail);
        return productRepository.save(product);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with this id : " + id));
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
