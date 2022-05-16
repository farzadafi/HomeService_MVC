package com.example.HomeService_MVC.validation.servicesName;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ServicesNameConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface ServicesName {

    String message() default "This name is defined before!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
