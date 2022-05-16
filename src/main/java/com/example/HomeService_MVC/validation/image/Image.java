package com.example.HomeService_MVC.validation.image;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ImageConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Image {

    String message() default "select just jpg image!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
