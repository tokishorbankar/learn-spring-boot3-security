package com.kb.learn.security.user;

import com.kb.learn.validation.PasswordMatches;
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
@PasswordMatches
@ToString
public class RegisterRequest {

    @NotEmpty(message = "First name required")
    private String firstname;

    @NotEmpty(message = "Last name required")
    private String lastname;

    @NotEmpty(message = "Email required")
    @Email(message = "Invalid email",
            regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @ValidPassword
    @NotEmpty(message = "Password required")
    private String password;

    @NotEmpty(message = "Confirm Password Required")
    private String confirmPassword;

    private boolean isAdmin = false;
}
