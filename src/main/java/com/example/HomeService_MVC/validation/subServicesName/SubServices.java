package com.example.HomeService_MVC.validation.subServicesName;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SubServicesConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface SubServices {

    String message() default "This name is defined before!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
