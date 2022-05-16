package com.example.HomeService_MVC.validation.servicesName;

import com.example.HomeService_MVC.service.impel.ServicesServiceImpel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ServicesNameConstraintValidator implements ConstraintValidator<ServicesName,String> {

    private final ServicesServiceImpel servicesServiceImpel;

    public ServicesNameConstraintValidator(ServicesServiceImpel servicesServiceImpel) {
        this.servicesServiceImpel = servicesServiceImpel;
    }

    @Override
    public void initialize(ServicesName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String servicesName, ConstraintValidatorContext constraintValidatorContext) {
        return servicesServiceImpel.findByServicesName(servicesName) == null;
    }
}
