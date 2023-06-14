package ru.yandex.practicum.filmorate.utils.validation.impl;

import ru.yandex.practicum.filmorate.utils.validation.constraints.NotInFuture;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class NotInFutureValidator implements ConstraintValidator<NotInFuture, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isBefore(LocalDate.now());
    }
}
