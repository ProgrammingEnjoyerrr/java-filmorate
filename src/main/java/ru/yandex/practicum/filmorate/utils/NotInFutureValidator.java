package ru.yandex.practicum.filmorate.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class NotInFutureValidator implements ConstraintValidator<NotInFuture, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isBefore(LocalDate.now());
    }
}
