package com.steverado.user_service.service.Impl;

import com.steverado.user_service.dto.LoginUserDto;
import com.steverado.user_service.dto.RegisterUserDto;
import com.steverado.user_service.entity.User;
import com.steverado.user_service.enums.Role;
import com.steverado.user_service.exception.NotAdminException;
import com.steverado.user_service.exception.UserNotFoundException;
import com.steverado.user_service.mapper.UserMapper;
import com.steverado.user_service.repository.UserRepository;
import com.steverado.user_service.response.ApiResponse;
import com.steverado.user_service.response.CreateUserResponse;
import com.steverado.user_service.response.LoginResponse;
import com.steverado.user_service.service.JwtService;
import com.steverado.user_service.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<ApiResponse> signup(RegisterUserDto input) {
        log.info("Received request to register a user with name: {}", input.getFirstName() + " " + input.getLastName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("successful authentication for user {} ", authentication.getName());

        String email = authentication.getName();

        User currentUser = findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (currentUser.getRole() != Role.ADMIN) {
            throw new NotAdminException("FORBIDDEN!");
        }

        User user = userMapper.toUserEntity(input);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.saveUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender(),
                user.getRole().name(),
                user.getDepartment(),
                user.getAddress()
        );
        User savedUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("successful fetched logged in user  with id {} ", savedUser.getId());


        CreateUserResponse data = new CreateUserResponse();
        data.setMessage("User account successfully created");
        data.setUserId(savedUser.getId());
        data.setExpiresIn(jwtService.getExpirationTime());

        ApiResponse response = new ApiResponse("Success", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse> login(LoginUserDto input) {
        log.info("Received request to login a user with email and password: {}", input.getEmail() + " " + input.getPassword());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        User authenticatedUser = userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new UserNotFoundException("user not found"));
        log.info("Successfully authenticated user with id: {}", authenticatedUser.getId());

        String jwtToken = jwtService.generateToken(authenticatedUser);
        log.info("Successfully extracted claims from token : {}", jwtService.extractAllClaims(jwtToken));


        LoginResponse data = new LoginResponse();
        data.setToken(jwtToken);
        data.setUserId(authenticatedUser.getId());
        data.setExpiresIn(jwtService.getExpirationTime());

        ApiResponse response = new ApiResponse("Success", data);

        return ResponseEntity.ok(response);
    }
}
