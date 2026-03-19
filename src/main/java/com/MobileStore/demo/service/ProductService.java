package com.MobileStore.demo.service;

import com.MobileStore.demo.entity.Product;
import com.MobileStore.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchByName(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword);
        }
        return productRepository.findAll();
    }
    public void saveProduct(Product product) {
        if (product.getStock() == null || product.getStock() <= 0) {
            product.setStockStatus("Out of Stock");
        } else if (product.getStock() < 10) {
            product.setStockStatus("Low Stock");
        } else {
            product.setStockStatus("In Stock");
        }
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}