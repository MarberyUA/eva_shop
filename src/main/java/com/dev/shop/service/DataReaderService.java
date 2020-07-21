package com.dev.shop.service;

import com.dev.shop.dto.ProductRequestDto;

import java.util.List;

public interface DataReaderService {
    List<ProductRequestDto> getDataFromFile(String filePath);
}
