package com.example.HomeService_MVC.validation.subServicesName;

import com.example.HomeService_MVC.service.impel.SubServicesServiceImpel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SubServicesConstraintValidator implements ConstraintValidator<SubServices,String> {

    private final SubServicesServiceImpel subServicesServiceImpel;

    public SubServicesConstraintValidator(SubServicesServiceImpel subServicesServiceImpel) {
        this.subServicesServiceImpel = subServicesServiceImpel;
    }

    @Override
    public void initialize(SubServices constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String subServicesName, ConstraintValidatorContext constraintValidatorContext) {
        return subServicesServiceImpel.findBySubServicesName(subServicesName) == null;
    }
}
