package com.dev.shop.controller;

import com.dev.shop.dto.ProductRequestDto;
import com.dev.shop.dto.mapper.ProductMapper;
import com.dev.shop.model.Product;
import com.dev.shop.service.DataReaderService;
import com.dev.shop.service.ProductService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/products")
public class ProductController {
    private ProductService productService;
    private DataReaderService dataReaderService;
    private ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, DataReaderService dataReaderService,
                             ProductMapper productMapper) {
        this.dataReaderService = dataReaderService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostConstruct
    public void init() {
        List<ProductRequestDto> list = dataReaderService
                .getDataFromFile("src/main/resources/products.txt");
        for (ProductRequestDto dto : list) {
            productService.create(productMapper.mapDtoToProduct(dto));
        }
    }

    @GetMapping
    public List<Product> get(@RequestParam String nameFilter) {
        return productService.findByNameRegex(nameFilter);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ProductRequestDto dto) {
        Product pr = productService.create(productMapper.mapDtoToProduct(dto));
        Map<Object, Object> body = new HashMap<>();
        body.put("message", "Product was created successfully!");
        body.put("product", pr);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestParam String id,
                                         @RequestBody ProductRequestDto dto) {
        Product pr = productMapper.mapDtoToProduct(dto);
        Product productFoundById = productService.findById(Long.parseLong(id));
        productFoundById.setName(pr.getName());
        productFoundById.setDescription(pr.getDescription());
        productService.create(productFoundById);
        Map<Object, Object> body = new HashMap<>();
        body.put("message", "product updated successfully!");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam String id) {
        productService.delete(productService.findById(Long.parseLong(id)));
        Map<Object, Object> body = new HashMap<>();
        body.put("message", "product was deleted successfully!");
        return ResponseEntity.ok(body);
    }
}
