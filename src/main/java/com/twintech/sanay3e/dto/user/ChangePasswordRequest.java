package com.twintech.sanay3e.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO representing a password change request")
public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required")
    @Schema(description = "The user's current password", example = "StrongP@ss123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 100, message = "New password must be between 8 and 100 characters")
    @Schema(description = "The new password to set", example = "NewStrongP@ss123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;

    @NotBlank(message = "Confirm new password is required")
    @Schema(description = "Confirmation of the new password", example = "NewStrongP@ss123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmNewPassword;
}
