package com.example.HomeService_MVC.validation.email;

import com.example.HomeService_MVC.service.impel.UserServiceImpel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<Email,String> {

    private final UserServiceImpel userServiceImpel;

    public EmailConstraintValidator(UserServiceImpel userServiceImpel) {
        this.userServiceImpel = userServiceImpel;
    }

    @Override
    public void initialize(Email constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(userServiceImpel.existsByEmail(email))
            return false;
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
