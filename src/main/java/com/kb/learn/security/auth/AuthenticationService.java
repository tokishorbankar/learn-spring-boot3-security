package com.kb.learn.security.auth;

import com.kb.learn.security.config.JwtService;
import com.kb.learn.security.user.Role;
import com.kb.learn.security.user.User;
import com.kb.learn.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static AuthenticationResponse getAuthenticationResponse(final String jwtToken) {
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse register(final RegisterRequest request) {

        var user = User
                .builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.isAdmin() ? Role.ADMIN : Role.USER)
                .build();

        String jwtToken = getJwtToken(userRepository.save(user));

        return getAuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        Authentication authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        if (!authenticated.isAuthenticated())
            throw new AccountExpiredException(String.format("User %s account is blocked", request.getEmail()));


        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", request.getEmail())));

        String jwtToken = getJwtToken(user);

        return getAuthenticationResponse(jwtToken);
    }

    private String getJwtToken(final User user) {
        return jwtService.generateJwtToken(user);
    }
}
