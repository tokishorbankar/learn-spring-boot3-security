package com.kb.learn.security.user;

import com.kb.learn.module.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RegistrationRestController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info(String.format("Register request {}", request));
        return ResponseEntity.ok(userService.register(request));
    }
}
