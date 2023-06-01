package ru.yandex.practicum.filmorate.utils.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.utils.validation.constraints.NoSpaces;

public class NoSpacesValidator implements ConstraintValidator<NoSpaces, CharSequence> {

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        return !charSequence.toString().contains(" ");
    }
}
