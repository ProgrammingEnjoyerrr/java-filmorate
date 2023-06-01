package ru.yandex.practicum.filmorate.utils.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.utils.validation.impl.MaxByteLengthValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MaxByteLengthValidator.class})
public @interface MaxByteLength {
    int value();

    String message() default "превышено максимальное допустимое количество символов в строке";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
