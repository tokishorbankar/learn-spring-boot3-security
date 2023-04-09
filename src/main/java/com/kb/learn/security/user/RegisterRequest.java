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

    @NotEmpty(message = "First name is mandatory")
    private String firstname;

    @NotEmpty(message = "Last name is mandatory")
    private String lastname;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email invalid",
            regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @ValidPassword
    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotEmpty(message = "Confirm Password is mandatory")
    private String confirmPassword;

    private boolean isAdmin = false;
}
