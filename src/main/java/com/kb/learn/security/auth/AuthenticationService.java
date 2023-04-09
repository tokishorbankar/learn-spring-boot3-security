package com.kb.learn.security.auth;

import com.kb.learn.exception.UnauthorizedUserException;
import com.kb.learn.exception.UserNotFoundException;
import com.kb.learn.module.ApiResponse;
import com.kb.learn.security.config.JwtService;
import com.kb.learn.security.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    public ApiResponse<String> authenticate(final @Valid AuthenticationRequest request) {
        log.debug("Authenticating with request {}", request);

        Authentication authenticated = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (!authenticated.isAuthenticated())
            throw new UnauthorizedUserException(String.format("Unable to process your request %s, Please try with valid credentials.", request.getEmail()));

        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("Requested user %s account not exist", request.getEmail())));

        String jwtToken = jwtService.generateJwtToken(user);

        return new ApiResponse<>(jwtToken);
    }

}
