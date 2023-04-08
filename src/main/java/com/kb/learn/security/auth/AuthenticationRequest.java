package com.kb.learn.security.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
@ToString
public class AuthenticationRequest {

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
