package com.dev.shop.service;

import com.dev.shop.model.Product;
import java.util.List;

public interface ProductService {

    Product create(Product product);

    List<Product> findByNameRegex(String regex);

    Product findById(Long id);

    void delete(Product product);
}
