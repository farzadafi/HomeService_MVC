package com.example.HomeService_MVC.validation.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password,String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password.length() < 8 )
            return false;
        char[] passwordArray = password.toCharArray();
        char[] signArray =  new char[] {'!','@','#','$','%','^','&','*','(',')','-','+','=','.',',','>','<','?','/','|',':',';'};
        int space = 0,lowerCase = 0,upperCase = 0,sign = 0,digit = 0;
        for (char c : passwordArray)
            if (Character.isSpaceChar(c))
                ++space;
        for (char c : passwordArray)
            if (Character.isUpperCase(c))
                ++upperCase;
        for (char c : passwordArray)
            if (Character.isLowerCase(c))
                ++lowerCase;
        for (char c : passwordArray)
            if (Character.isDigit(c))
                ++digit;
        for (char c : signArray)
            for (char value : passwordArray)
                if (c == value)
                    ++sign;
        return (space != 0) && (lowerCase != 0) && (upperCase != 0) && (sign != 0) && (digit != 0);
    }
}
