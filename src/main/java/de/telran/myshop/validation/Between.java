package de.telran.myshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // сможем использовать эту аннотацию только для полей
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BetweenValidator.class) // какой класс будет проверять
public @interface Between {
    String message() default "{Between.invalid}";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};

    double from(); // нижняя граница
    double to(); // верхняя граница
}
