package com.twintech.sanay3e.service;

import com.twintech.sanay3e.Repository.UserRepository;
import com.twintech.sanay3e.dto.user.UserProfileResponse;
import com.twintech.sanay3e.dto.user.UpdateProfileRequest;
import com.twintech.sanay3e.dto.user.UpdateProfileResponse;
import com.twintech.sanay3e.dto.user.ChangePasswordRequest;
import com.twintech.sanay3e.entity.User;
import com.twintech.sanay3e.entity.enums.UserStatus;
import com.twintech.sanay3e.exception.ResourceNotFoundException;
import com.twintech.sanay3e.mapper.UserMapper;
import com.twintech.sanay3e.security.CustomUserDetailsService;
import com.twintech.sanay3e.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Fetches user entity by ID and maps it to UserProfileResponse.
     *
     * @param userId The ID of the user to fetch.
     * @return UserProfileResponse details.
     * @throws ResponseStatusException 404 NOT_FOUND if user does not exist.
     */
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {
        log.info("Fetching user profile for userId: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        return userMapper.toUserProfileResponse(user);
    }


    @Transactional
    public UpdateProfileResponse updateUserProfile(Long userId, UpdateProfileRequest request) {
        log.info("Updating user profile for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        String oldPhone = user.getPhone() != null ? user.getPhone().trim() : "";
        String oldEmail = user.getEmail() != null ? user.getEmail().trim() : "";

        String newPhone = request.getPhone() != null ? request.getPhone().trim() : null;
        String newEmail = request.getEmail() != null ? request.getEmail().trim() : null;

        boolean phoneChanged = false;

        // 1. If phone is changing, validate uniqueness
        if (newPhone != null && !newPhone.isEmpty() && !newPhone.equals(oldPhone)) {
            userRepository.findByPhone(newPhone).ifPresent(existingUser -> {
                if (!existingUser.getId().equals(userId)) {
                    throw new IllegalArgumentException("Phone number is already registered by another user");
                }
            });
            phoneChanged = true;
        }

        // 2. If email is changing, validate uniqueness
        if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(oldEmail)) {
            userRepository.findByEmail(newEmail).ifPresent(existingUser -> {
                if (!existingUser.getId().equals(userId)) {
                    throw new IllegalArgumentException("Email is already registered by another user");
                }
            });
        }

        // 3. Map updates to existing entity using MapStruct
        userMapper.updateUserFromDto(request, user);

        User savedUser = userRepository.save(user);
        log.info("User profile updated successfully for userId: {}", userId);

        // 4. Generate new token if phone changed
        String newAccessToken = null;
        if (phoneChanged) {
            log.info("Phone number changed for userId: {}. Regenerating access token.", userId);

            var authorities = userDetailsService.loadUserByUsername(request.getPhone()).getAuthorities();

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(savedUser.getPhone())
                    .password("")
                    .authorities(authorities)
                    .build();

            newAccessToken = jwtUtil.generateToken(userDetails, savedUser.getId());

            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", savedUser.getId());
            claims.put("phone", savedUser.getPhone());

            UsernamePasswordAuthenticationToken newAuthToken =
                    new UsernamePasswordAuthenticationToken(claims, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(newAuthToken);
            log.info("Security Context successfully updated with Map Principal for phone: {}", savedUser.getPhone());
        }

        UserProfileResponse profileResponse = userMapper.toUserProfileResponse(savedUser);
        return UpdateProfileResponse.builder()
                .profile(profileResponse)
                .newAccessToken(newAccessToken)
                .build();
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        log.info("Attempting to change password for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            log.warn("Incorrect current password provided for userId: {}", userId);
            throw new IllegalArgumentException("Incorrect current password");
        }

        if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            log.warn("New passwords do not match for userId: {}", userId);
            throw new IllegalArgumentException("New passwords do not match");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            log.warn("User tried to reuse the current password for userId: {}", userId);
            throw new IllegalArgumentException("New password cannot be the same as the current password");
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword().trim());
        user.setPasswordHash(encodedPassword);
        userRepository.save(user);

        log.info("Password changed successfully for userId: {}", userId);
    }

    /**
     * Soft deletes a user account by setting its status to DELETED.
     *
     * @param userId The ID of the user to delete.
     * @throws ResourceNotFoundException if the user is not found or already deleted.
     */
    @Transactional
    public void deleteUser(Long userId) {
        log.info("Attempting to delete user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (user.getStatus() == UserStatus.DELETED) {
            log.warn("User with ID: {} is already deleted", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);

        log.info("User with ID: {} has been soft-deleted successfully", userId);
    }
}
