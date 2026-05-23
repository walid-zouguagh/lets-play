package com.lets_play.service;

import com.lets_play.model.Product;
import com.lets_play.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        product.setUserId(currentUserEmail);
        return productRepository.save(product);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product updateProduct(String id, Product updatedProduct) {
        Product existingProduct = getProductById(id);

        // Secure it: Check if the user is authorized to modify this item
        validateOwnershipOrAdmin(existingProduct.getUserId());

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        Product product = getProductById(id);

        // Secure it: Check if the user is authorized to delete this item
        validateOwnershipOrAdmin(product.getUserId());

        productRepository.deleteById(id);
    }

    // Helper validation logic
    private void validateOwnershipOrAdmin(String productOwnerEmail) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Check if user has the ROLE_ADMIN authority
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // If they are neither the owner nor an admin, block them!
        if (!currentUsername.equals(productOwnerEmail) && !isAdmin) {
            throw new RuntimeException("Access Denied: You do not own this product!");
        }
    }
}