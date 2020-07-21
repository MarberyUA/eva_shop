package com.dev.shop.dto;

import com.dev.shop.lib.EmailConstraint;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticationRegisterRequestDto {
    @NotNull(message = "Username must not be null")
    private String username;
    @NotNull(message = "Email must not be null")
    @EmailConstraint
    private String email;
    @NotNull(message = "Password must not be null")
    @Min(value = 10, message = "Password length must be more then 10!")
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
