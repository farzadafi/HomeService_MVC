package com.example.HomeService_MVC.validation.description;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescriptionConstraintValidator implements ConstraintValidator<Description,String> {
    @Override
    public void initialize(Description constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String description, ConstraintValidatorContext constraintValidatorContext) {
            int space = 0;
            for (Character ch:description.toCharArray()
            ) {
                if(Character.isSpaceChar(ch))
                    space++;
            }
            int alpha = description.length() - space;
        return description.length() >= space / 2 + 1;
    }
}
