package com.kb.learn.security.auth;

import com.kb.learn.validation.PasswordMatches;
import com.kb.learn.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@PasswordMatches
@ToString
public class RegisterRequest {

    @NotBlank(message = "First name is mandatory")
    private String firstname;

    @NotBlank(message = "Last name is mandatory")
    private String lastname;

    @Email(message = "Email is mandatory")
    private String email;

    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Confirm Password is mandatory")
    private String confirmPassword;

    private boolean isAdmin = false;
}
