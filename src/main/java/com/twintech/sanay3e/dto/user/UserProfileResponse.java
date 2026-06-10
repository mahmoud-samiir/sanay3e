package com.twintech.sanay3e.dto.user;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO representing user profile details")
public class UserProfileResponse {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Full name of the user", example = "Mahmoud Samir")
    private String name;

    @Schema(description = "Phone number of the user", example = "+2010000000")
    private String phone;

    @Schema(description = "Email address of the user", example = "mahmoud.samir@example.com")
    private String email;

    @Schema(description = "Type of user (e.g. CLIENT, PROVIDER, ADMIN)", example = "CLIENT")
    private String type;

    @Schema(description = "Account status of the user (e.g. ACTIVE, SUSPENDED, DELETED)", example = "ACTIVE")
    private String status;

    @Schema(description = "Timestamp when the user account was created", example = "2026-06-09T13:03:12")
    private LocalDateTime createdAt;
}
