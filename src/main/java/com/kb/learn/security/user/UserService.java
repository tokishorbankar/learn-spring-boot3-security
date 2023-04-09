package com.kb.learn.security.user;

import com.kb.learn.exception.UserAlreadyExistException;
import com.kb.learn.module.ApiResponse;
import com.kb.learn.validation.PasswordMatches;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Validated
@PasswordMatches
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<User> register(final @Valid RegisterRequest request) {
        log.debug("Register user {}", request);

        if (isUserExist(request.getEmail()))
            throw new UserAlreadyExistException(String.format("User %s account is already exists.", request.getEmail()));

        var user = User
                .builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.isAdmin() ? Role.ADMIN : Role.USER).build();

        validateUserDetails(user);

        var saved = userRepository.save(user);
        return new ApiResponse<>(saved);
    }

    private boolean isUserExist(@Email final String email) {
        return userRepository.existsByEmail(email);
    }

    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");
        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }
}
