package com.dev.shop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticationLoginRequestDto {
    @NotNull(message = "Username must not be null")
    private String username;
    @NotNull(message = "Password must not be null")
    private String password;
}
