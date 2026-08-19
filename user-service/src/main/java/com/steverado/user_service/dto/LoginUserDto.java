package com.steverado.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotBlank(message = "email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;
}
