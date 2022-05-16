package com.example.HomeService_MVC.validation.name;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameConstraintValidator implements ConstraintValidator<Name,String> {
    @Override
    public void initialize(Name constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name.length() < 3)
            return false;
        for (Character ch : name.toCharArray()
        ) {
            if (Character.isDigit(ch))
                return false;
        }
            for (Character ch : name.toCharArray()
            ) {
                if (!Character.isAlphabetic(ch))
                    return false;
        }
        return true;
    }
}
