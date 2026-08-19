package com.steverado.user_service.mapper;

import com.steverado.user_service.dto.RegisterUserDto;
import com.steverado.user_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserEntity(RegisterUserDto registerUserDto);
}
