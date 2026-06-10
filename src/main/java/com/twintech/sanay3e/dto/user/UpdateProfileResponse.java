package com.twintech.sanay3e.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO representing profile update result, containing the updated profile and a regenerated access token if the phone number changed")
public class UpdateProfileResponse {

    @Schema(description = "Updated user profile details")
    private UserProfileResponse profile;

    @Schema(description = "Newly generated JWT access token (only populated if phone number was updated)")
    private String newAccessToken;
}
