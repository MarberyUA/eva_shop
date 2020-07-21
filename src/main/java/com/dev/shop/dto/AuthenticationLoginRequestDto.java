package com.dev.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationLoginRequestDto {
    private String username;
    private String password;
}