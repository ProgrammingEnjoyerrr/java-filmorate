package ru.yandex.practicum.filmorate.utils.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.utils.validation.constraints.MaxByteLength;

import java.nio.charset.Charset;

public class MaxByteLengthValidator implements ConstraintValidator<MaxByteLength, String> {
    private int max;
    public void initialize(MaxByteLength constraintAnnotation) {
        this.max = constraintAnnotation.value();
    }
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return object == null || object.getBytes(Charset.defaultCharset()).length <= this.max;
    }
}