package com.kb.learn.security.auth;

import com.kb.learn.security.config.JwtService;
import com.kb.learn.security.user.Role;
import com.kb.learn.security.user.User;
import com.kb.learn.security.user.UserRepository;
import com.kb.learn.validation.PasswordMatches;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;


@Service
@RequiredArgsConstructor
@Validated
@PasswordMatches
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static AuthenticationResponse getAuthenticationResponse(final String jwtToken) {
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse register(final @Valid RegisterRequest request) {
        log.debug(String.format("register {}", request));
        if (isUserExist(request.getEmail()))
            throw new RuntimeException(String.format("User %s account is already exists.", request.getEmail()));
        var user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(request.isAdmin() ? Role.ADMIN : Role.USER).build();
        validateUserDetails(user);
        String jwtToken = getJwtToken(userRepository.save(user));
        return getAuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(final @Valid AuthenticationRequest request) {
        log.debug(String.format("authenticate {}", request));
        Authentication authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (!authenticated.isAuthenticated())
            throw new RuntimeException(String.format("User %s account is not authenticated", request.getEmail()));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", request.getEmail())));
        String jwtToken = getJwtToken(user);
        return getAuthenticationResponse(jwtToken);
    }

    private boolean isUserExist(final String email) {
        return userRepository.existsByEmail(email);
    }

    private String getJwtToken(final User user) {
        return jwtService.generateJwtToken(user);
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
