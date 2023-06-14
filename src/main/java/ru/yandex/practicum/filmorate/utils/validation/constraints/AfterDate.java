package ru.yandex.practicum.filmorate.utils.validation.constraints;

import ru.yandex.practicum.filmorate.utils.validation.impl.AfterDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AfterDateValidator.class})
public @interface AfterDate {
    String value();

    String message() default "дата не может быть раньше чем заданная";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
