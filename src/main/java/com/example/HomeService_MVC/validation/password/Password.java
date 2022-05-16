package com.example.HomeService_MVC.validation.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "at least 8 and have space,alpha,number,low and upper case!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
