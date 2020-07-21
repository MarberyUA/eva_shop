package com.dev.shop.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationLoginRequestDto {
    @NotNull(message = "Username must not be null")
    private String username;
    @NotNull(message = "Password must not be null")
    private String password;
}
