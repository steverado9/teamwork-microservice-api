package com.steverado.user_service.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserResponse {

    private Long userId;
    private Long expiresIn;
    private String message;
}
