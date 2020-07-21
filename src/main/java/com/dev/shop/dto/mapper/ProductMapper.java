package com.dev.shop.dto.mapper;

import com.dev.shop.dto.ProductRequestDto;
import com.dev.shop.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    private final Logger logger = LogManager.getLogger(ProductMapper.class);

    public Product mapDtoToProduct(ProductRequestDto dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(dto);
            Product product = objectMapper.readValue(jsonData, Product.class);
            return product;
        } catch (JsonProcessingException e) {
            logger.info("There is an error while creating User!");
            throw new RuntimeException("There is an error while creating Product! "
                    + "Check if you entered valid data!");
        }
    }
}
