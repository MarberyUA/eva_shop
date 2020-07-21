package com.dev.shop.lib.validator;

import com.dev.shop.lib.EmailConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    public static final String EMAIL_CHECKIN_REGEX = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$";

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext
            constraintValidatorContext) {
        return emailField != null && emailField.matches(EMAIL_CHECKIN_REGEX);
    }
}