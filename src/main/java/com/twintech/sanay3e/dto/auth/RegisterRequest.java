package com.twintech.sanay3e.dto.auth;

import com.twintech.sanay3e.entity.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "User's full name", example = "Mahmoud Samir", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 20, message = "Phone must be between 10 and 20 characters")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Phone number must contain only digits and an optional leading '+'")
    @Schema(description = "User's phone number", example = "+20100000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 150, message = "Email must be less than 150 characters")
    @Schema(description = "User's email address", example = "mahmoud.samir@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
    @Schema(description = "User's account password", example = "StrongP@ss123",
            requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @NotNull(message = "User type is required")
    @Schema(description = "Type of the user account", example = "CLIENT",requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;
}