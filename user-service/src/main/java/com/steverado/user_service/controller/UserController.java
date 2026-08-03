package com.steverado.user_service.controller;

import com.steverado.user_service.dto.RegisterUserDto;
import com.steverado.user_service.response.ApiResponse;
import com.steverado.user_service.service.JwtService;
import com.steverado.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final JwtService jwtService;

    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {

        return userService.signup(registerUserDto);
    }
}
