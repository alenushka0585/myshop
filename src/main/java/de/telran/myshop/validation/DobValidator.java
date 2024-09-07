package de.telran.myshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Calendar;

import java.util.Date;

public class DobValidator implements ConstraintValidator<ValidDob, Date> {
    @Override
    public void initialize(ValidDob constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -100); // Уменьшаем дату на 100 лет
        Date hundredYearsAgo = calendar.getTime();

        return !date.before(hundredYearsAgo); // Дата не должна быть раньше чем 100 лет назад
    }
}
