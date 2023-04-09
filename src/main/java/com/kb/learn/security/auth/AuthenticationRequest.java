package com.kb.learn.security.auth;

import com.kb.learn.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class AuthenticationRequest {

    @NotEmpty(message = "Username(Email) required")
    @Email(message = "Invalid email",
            regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @ValidPassword()
    @NotEmpty(message = "Password required")
    private String password;
}
