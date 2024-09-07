package de.telran.myshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class BetweenValidator implements ConstraintValidator<Between, BigDecimal> {
    private BigDecimal from = BigDecimal.valueOf(0);
    private BigDecimal to = BigDecimal.valueOf(0);

    // испльзуется для использования внутренних полей
    @Override
    public void initialize(Between constraintAnnotation) {
        from = BigDecimal.valueOf(constraintAnnotation.from());
        to = BigDecimal.valueOf(constraintAnnotation.to());
    }

    @Override
    public boolean isValid(BigDecimal b, ConstraintValidatorContext context) {
        return b.compareTo(from) >= 0 && b.compareTo(to) <= 0;
    }
}
