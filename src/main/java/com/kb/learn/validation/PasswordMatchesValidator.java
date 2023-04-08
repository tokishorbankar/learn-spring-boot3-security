package com.kb.learn.validation;


import com.kb.learn.security.auth.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches passwordMatches) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        var user = (RegisterRequest) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }

}