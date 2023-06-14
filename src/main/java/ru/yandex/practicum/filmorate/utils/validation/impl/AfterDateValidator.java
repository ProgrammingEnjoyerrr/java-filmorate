package ru.yandex.practicum.filmorate.utils.validation.impl;

import ru.yandex.practicum.filmorate.utils.validation.constraints.AfterDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {
    private LocalDate date;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize(AfterDate constraintAnnotation) {
        this.date = LocalDate.parse(constraintAnnotation.value(), FORMATTER);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(date);
    }
}
