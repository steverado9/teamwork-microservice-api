package com.steverado.user_service.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private Long userId;
    private Long expiresIn;
}
