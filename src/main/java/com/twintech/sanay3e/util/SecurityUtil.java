package com.twintech.sanay3e.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@UtilityClass
public class SecurityUtil {

    /**
     * Extracts the authenticated userId from the SecurityContext.
     * Handles cases where the principal is a Map (JWT claims map).
     *
     * @return The authenticated user's ID
     * @throws ResponseStatusException 401 UNAUTHORIZED if user is not authenticated or userId is invalid/missing
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        // 1. Handle claims map stored in Principal
        if (principal instanceof Map) {
            Map<?, ?> claims = (Map<?, ?>) principal;
            Object userIdObj = claims.get("userId");

            if (userIdObj != null) {
                return convertToLong(userIdObj);
            }
        }

        // 2. Handle standard UserDetails fallback (if authentication principal is a Spring Security UserDetails)
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            // UserDetails contains the username (phone/email in this project).
            // Usually, standard JWT configs might store user details here.
            // If the principal is UserDetails but we need Long id, we expect claims to be in a Map.
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User ID not found in Security Context");
    }

    private Long convertToLong(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else if (obj instanceof String) {
            try {
                return Long.parseLong((String) obj);
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user ID format in credentials");
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user ID type in credentials");
    }
}
