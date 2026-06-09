package com.twintech.sanay3e.mapper;

import com.twintech.sanay3e.dto.auth.AuthResponse;
import com.twintech.sanay3e.dto.auth.RegisterRequest;
import com.twintech.sanay3e.entity.User;
import com.twintech.sanay3e.entity.enums.UserStatus;
import com.twintech.sanay3e.entity.enums.UserType;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toUser(RegisterRequest request, String encodedPassword) {
        if (request == null) {
            return null;
        }

        UserType userType = UserType.CLIENT;
        if (request.getType() != null && !request.getType().trim().isEmpty()) {
            try {
                userType = UserType.valueOf(request.getType().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                userType = UserType.CLIENT;
            }
        }

        return User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .passwordHash(encodedPassword)
                .type(userType)
                .status(UserStatus.ACTIVE)
                .build();
    }

    public AuthResponse toAuthResponse(User user, String token) {
        if (user == null) {
            return null;
        }

        return AuthResponse.builder()
                .token(token)
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .type(user.getType().name())
                .build();
    }
}
