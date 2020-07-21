package com.dev.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRegisterRequestDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}