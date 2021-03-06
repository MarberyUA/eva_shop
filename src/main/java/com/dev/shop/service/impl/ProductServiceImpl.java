package com.dev.shop.service.impl;

import com.dev.shop.model.Product;
import com.dev.shop.repository.ProductRepository;
import com.dev.shop.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findByNameRegex(String regex) {
        return productRepository.findByNameRegex(regex);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
