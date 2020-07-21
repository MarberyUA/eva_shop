package com.dev.shop.service.impl;

import com.dev.shop.dto.ProductRequestDto;
import com.dev.shop.service.DataReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DataReaderServiceImpl implements DataReaderService {

    @Override
    public List<ProductRequestDto> getDataFromFile(String filePath) {
        List<ProductRequestDto> list = new ArrayList<>();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath));) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                ProductRequestDto dto = new ProductRequestDto();
                String[] splitRow = row.split(",", 2);
                dto.setName(splitRow[0]);
                dto.setDescription(splitRow[1]);
                list.add(dto);
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found,"
                    + " please check the pass to the file!", e);
        }
        return list;
    }
}
