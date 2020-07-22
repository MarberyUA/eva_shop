package com.dev.shop.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    @NotNull(message = "Name must not be null")
    private String name;
    @NotNull(message = "Description must not be null")
    private String description;
}
