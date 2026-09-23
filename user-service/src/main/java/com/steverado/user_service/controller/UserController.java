package com.steverado.user_service.controller;

import com.steverado.user_service.dto.LoginUserDto;
import com.steverado.user_service.dto.RegisterUserDto;
import com.steverado.user_service.entity.User;
import com.steverado.user_service.response.ApiResponse;
import com.steverado.user_service.service.UserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return currentUser;
    }

    @PostMapping("/signup")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {

        return userService.signup(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) {

        return userService.login(loginUserDto);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public Optional<User> getUserById (@PathVariable Long id) {

        return userService.findUserById(id);
    }
}
