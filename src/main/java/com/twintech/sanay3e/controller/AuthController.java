package com.twintech.sanay3e.controller;

import com.twintech.sanay3e.dto.Sanay3eResponse;
import com.twintech.sanay3e.dto.auth.AuthResponse;
import com.twintech.sanay3e.dto.auth.LoginRequest;
import com.twintech.sanay3e.dto.auth.RegisterRequest;
import com.twintech.sanay3e.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Sanay3e security endpoints.")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account (CLIENT or PROVIDER) and returns an authentication token.")
    public ResponseEntity<Sanay3eResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {

        AuthResponse authResponse = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Sanay3eResponse.success("User registered successfully", authResponse));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user by email/phone and password, and returns a JWT token.")
    public ResponseEntity<Sanay3eResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.login(request);

        return ResponseEntity
                .ok(Sanay3eResponse.success("Logged in successfully", authResponse));
    }
}
