package com.steverado.user_service.dto;

import com.steverado.user_service.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotBlank(message = "firstname field should not be empty")
    private String firstName;

    @NotBlank(message = "lastname field should not be empty")
    private String lastName;

    @NotBlank(message = "email field should not be empty")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "password field should not be empty")
    private String password;

    @NotBlank(message = "gender field should not be empty")
    private String gender;

    @NotNull(message = "role field should not be empty")
    private Role role;

    @NotBlank(message = "department field should not be empty")
    private String department;

    @NotBlank(message = "address field should not be empty")
    private String address;
}
