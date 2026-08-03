package com.steverado.user_service.service.Impl;

import com.steverado.user_service.dto.RegisterUserDto;
import com.steverado.user_service.entity.User;
import com.steverado.user_service.enums.Role;
import com.steverado.user_service.mapper.UserMapper;
import com.steverado.user_service.repository.UserRepository;
import com.steverado.user_service.response.ApiResponse;
import com.steverado.user_service.response.CreateUserResponse;
import com.steverado.user_service.service.JwtService;
import com.steverado.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<ApiResponse> signup(RegisterUserDto registerUserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = findUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUser.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userMapper.toUserEntity(registerUserDto);
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
        User savedUser = userRepository.findByEmail(user.getEmail()).get();

        CreateUserResponse data = new CreateUserResponse();
        data.setMessage("User account successfully created");
        data.setUserId(savedUser.getId());
        data.setExpiresIn(jwtService.getExpirationTime());

        ApiResponse response = new ApiResponse("Success", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
