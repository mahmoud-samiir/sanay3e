package com.twintech.sanay3e.dto.auth;

import com.twintech.sanay3e.entity.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @Schema(description = "JWT Bearer token for API authorization", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "User's full name", example = "Mahmoud Samir")
    private String name;

    @Schema(description = "User's phone number", example = "+20100000000")
    private String phone;

    @Schema(description = "User's email address", example = "mahmoud.samir@example.com")
    private String email;

    @Schema(description = "Type of the user account")
    private String type;
}
