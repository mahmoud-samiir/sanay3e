package com.twintech.sanay3e.service;

import com.twintech.sanay3e.dto.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twintech.sanay3e.Repository.UserRepository;
import com.twintech.sanay3e.dto.auth.AuthResponse;
import com.twintech.sanay3e.dto.auth.LoginRequest;
import com.twintech.sanay3e.entity.User;
import com.twintech.sanay3e.mapper.AuthMapper;
import com.twintech.sanay3e.security.CustomUserDetailsService;
import com.twintech.sanay3e.security.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final AuthMapper authMapper;


    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Attempting to register new user with phone: {}", request.getPhone());
        // 1. Check if phone is already in use
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone number is already registered");
        }

        // 2. Check if email is already in use (if provided)
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email is already registered");
            }
        }

        // 3. Create and save new User using Mapper
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = authMapper.toUser(request, encodedPassword);
        User savedUser = userRepository.save(user);

        // 4. Generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getPhone());
        String token = jwtUtil.generateToken(userDetails, savedUser.getId());

        log.info("User registered successfully with phone: {} ", savedUser.getPhone());

        // 5. Map to AuthResponse using Mapper
        return authMapper.toAuthResponse(savedUser, token);
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Attempting login for identifier: {}", request.getIdentifier());
        // 1. Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword())
        );

        User user = userRepository.findByPhoneOrEmail(request.getIdentifier(), request.getIdentifier())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with: " + request.getIdentifier()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getIdentifier());
        String token = jwtUtil.generateToken(userDetails, user.getId());

        log.info("User {} logged in successfully", user.getPhone());

        // 3. Map to AuthResponse using Mapper
        return authMapper.toAuthResponse(user, token);
    }
}
