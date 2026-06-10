package com.twintech.sanay3e.controller;

import com.twintech.sanay3e.dto.Sanay3eResponse;
import com.twintech.sanay3e.dto.user.UserProfileResponse;
import com.twintech.sanay3e.dto.user.UpdateProfileRequest;
import com.twintech.sanay3e.dto.user.UpdateProfileResponse;
import com.twintech.sanay3e.dto.user.ChangePasswordRequest;
import com.twintech.sanay3e.util.SecurityUtil;
import com.twintech.sanay3e.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints for user profile and settings")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @Operation(
            summary = "Get current user profile",
            description = "Extracts the authenticated userId from the token context and returns user profile details."
    )
    public ResponseEntity<Sanay3eResponse<UserProfileResponse>> getProfile() {
        Long userId = SecurityUtil.getCurrentUserId();
        UserProfileResponse profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(Sanay3eResponse.success("User profile fetched successfully", profile));
    }

    @PutMapping("/profile")
    @Operation(
            summary = "Update current user profile",
            description = "Extracts the authenticated userId from the token context and updates name/email/phone details. Generates new token if phone changes."
    )
    public ResponseEntity<Sanay3eResponse<UpdateProfileResponse>> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        UpdateProfileResponse response = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(Sanay3eResponse.success("User profile updated successfully", response));
    }

    @PutMapping("/change-password")
    @Operation(
            summary = "Change user password",
            description = "Extracts the authenticated userId from the token context, verifies current password, and updates password securely."
    )
    public ResponseEntity<Sanay3eResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.changePassword(userId, request);
        return ResponseEntity.ok(Sanay3eResponse.success("Password updated successfully", "Password changed successfully"));
    }

    @DeleteMapping
    @Operation(
            summary = "Delete user account",
            description = "Extracts the authenticated userId from the token context and soft-deletes the user's account."
    )
    public ResponseEntity<Sanay3eResponse<Void>> deleteUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.deleteUser(userId);
        return ResponseEntity.ok(Sanay3eResponse.success("User account deleted successfully", null));
    }
}
