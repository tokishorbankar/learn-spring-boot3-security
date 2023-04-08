package com.kb.learn.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequest {

    @NonNull
    private String email;

    @NonNull
    private String password;
}
