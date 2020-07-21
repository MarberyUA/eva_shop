package com.dev.shop.controller;

import com.dev.shop.dto.ProductRequestDto;
import com.dev.shop.model.Product;
import com.dev.shop.service.DataReaderService;
import com.dev.shop.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/shop/products")
public class ProductController {
    private final Logger logger = LogManager.getLogger(ProductController.class);
    private ProductService productService;
    private DataReaderService dataReaderService;

    @Autowired
    public ProductController(ProductService productService, DataReaderService dataReaderService) {
        this.dataReaderService = dataReaderService;
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        List<ProductRequestDto> list = dataReaderService.getDataFromFile("src/main/resources/products.txt");
        ObjectMapper objectMapper = new ObjectMapper();
        for (ProductRequestDto dto : list) {
            try {
                String jsonData = objectMapper.writeValueAsString(dto);
                Product product = objectMapper.readValue(jsonData, Product.class);
                productService.create(product);
            } catch (JsonProcessingException e) {
                logger.info("There is an error while creating User!");
                throw new RuntimeException("There is an error while creating Product! "
                        + "Check if you entered valid data!");
            }
        }
    }

    @GetMapping
    public List<Product> get(@RequestParam String nameFilter) {
        return productService.findByNameRegex(nameFilter);
    }
}
