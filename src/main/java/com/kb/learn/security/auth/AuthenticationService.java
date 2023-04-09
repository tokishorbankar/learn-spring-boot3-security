package com.kb.learn.security.auth;

import com.kb.learn.module.ApiResponse;
import com.kb.learn.security.config.JwtService;
import com.kb.learn.security.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


@Service
@RequiredArgsConstructor
@Validated
@Slf4j
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ApiResponse authenticate(final @Valid AuthenticationRequest request) {
        log.debug(String.format("authenticate {}", request));

        Authentication authenticated = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (!authenticated.isAuthenticated())
            throw new RuntimeException(String.format("User %s account is not authenticated", request.getEmail()));

        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", request.getEmail())));

        String jwtToken = jwtService.generateJwtToken(user);

        return new ApiResponse(jwtToken);
    }

}
