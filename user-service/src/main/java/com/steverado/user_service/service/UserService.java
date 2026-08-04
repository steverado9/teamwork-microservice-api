package com.steverado.user_service.service;

import com.steverado.user_service.dto.LoginUserDto;
import com.steverado.user_service.dto.RegisterUserDto;
import com.steverado.user_service.entity.User;
import com.steverado.user_service.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);

    ResponseEntity<ApiResponse> signup(RegisterUserDto registerUserDto);


    ResponseEntity<ApiResponse> login(LoginUserDto loginUserDto);
}
