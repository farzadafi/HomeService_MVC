package com.example.HomeService_MVC.validation.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<Email,String> {
    @Override
    public void initialize(Email constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.length() < 10)
            return false;
        if(!email.matches(EMAIL_REGEX))
            return false;
        for (Character ch : email.toCharArray()
        ) {
            if (Character.isSpaceChar(ch))
                return false;
        }
        return true;
    }
}
