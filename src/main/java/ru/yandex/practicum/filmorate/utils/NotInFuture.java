package ru.yandex.practicum.filmorate.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotInFutureValidator.class})
public @interface NotInFuture {
    String message() default "дата не может быть в будущем";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
