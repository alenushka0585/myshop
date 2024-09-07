package de.telran.myshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DobValidator.class)
public @interface ValidDob {
    String message() default "{ValidDob.invalid}";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};

}
