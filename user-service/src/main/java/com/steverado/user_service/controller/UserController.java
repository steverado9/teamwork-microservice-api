package com.steverado.user_service.controller;

import com.steverado.user_service.dto.LoginUserDto;
import com.steverado.user_service.dto.RegisterUserDto;
import com.steverado.user_service.entity.User;
import com.steverado.user_service.response.ApiResponse;
import com.steverado.user_service.service.JwtService;
import com.steverado.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final JwtService jwtService;

    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/me")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return currentUser;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {

        return userService.signup(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) {

        return userService.login(loginUserDto);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById (@PathVariable Long id) {

        return userService.findUserById(id);
    }
}
