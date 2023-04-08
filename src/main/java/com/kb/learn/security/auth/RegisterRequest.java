package com.kb.learn.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private boolean isAdmin = false;
}
