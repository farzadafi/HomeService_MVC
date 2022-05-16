package com.example.HomeService_MVC.validation.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default "email more than 10 and have f@gmil.com regex!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
