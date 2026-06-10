package com.twintech.sanay3e.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO representing user profile update details")
public class UpdateProfileRequest {

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Updated full name of the user", example = "Mahmoud Samir2", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(min = 10, max = 20, message = "Phone must be between 10 and 20 characters")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Phone number must contain only digits and an optional leading '+'")
    @Schema(description = "Updated phone number of the user", example = "+20110000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    @Email(message = "Please provide a valid email address")
    @Size(max = 150, message = "Email must be less than 150 characters")
    @Schema(description = "Updated email address of the user", example = "mahmoud.s.samir@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
