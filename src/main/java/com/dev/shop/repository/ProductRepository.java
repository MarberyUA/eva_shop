package com.dev.shop.repository;

import com.dev.shop.model.Product;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM products pr WHERE pr.name NOT REGEXP ?1")
    List<Product> findByNameRegex(String regex);
}
