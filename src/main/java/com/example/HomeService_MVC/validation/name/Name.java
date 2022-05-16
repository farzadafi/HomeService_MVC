package com.example.HomeService_MVC.validation.name;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = NameConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    String message() default "name doesn't have number and must grater than 2 char!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
