package com.example.HomeService_MVC.validation.description;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DescriptionConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {

    String message() default "Please enter a correct description!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
