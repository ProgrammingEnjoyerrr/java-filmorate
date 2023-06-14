package ru.yandex.practicum.filmorate.utils.validation.impl;

import ru.yandex.practicum.filmorate.utils.validation.constraints.NoSpaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoSpacesValidator implements ConstraintValidator<NoSpaces, CharSequence> {

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        return !charSequence.toString().contains(" ");
    }
}
