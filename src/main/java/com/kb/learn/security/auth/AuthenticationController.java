package com.kb.learn.security.auth;

import com.kb.learn.module.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        log.info(String.format("Authenticate request {}", request));
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
