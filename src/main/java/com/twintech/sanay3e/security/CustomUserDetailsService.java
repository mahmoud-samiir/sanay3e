package com.twintech.sanay3e.security;

import com.twintech.sanay3e.Repository.UserRepository;
import com.twintech.sanay3e.entity.User;
import com.twintech.sanay3e.entity.enums.UserStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Try finding user by phone number
        User user = userRepository.findByPhoneOrEmail(identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean enabled = user.getStatus() == UserStatus.ACTIVE;
        boolean accountNonLocked = user.getStatus() != UserStatus.SUSPENDED;
        boolean accountNonExpired = user.getStatus() != UserStatus.DELETED;
        boolean credentialsNonExpired = true;

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getPhone()) // Map phone as username
                .password(user.getPasswordHash())
                .disabled(!enabled)
                .accountExpired(!accountNonExpired)
                .accountLocked(!accountNonLocked)
                .credentialsExpired(!credentialsNonExpired)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getType().name())))
                .build();
    }
}
