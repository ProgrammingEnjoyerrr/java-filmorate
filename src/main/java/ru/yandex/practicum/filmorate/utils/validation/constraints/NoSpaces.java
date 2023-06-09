package ru.yandex.practicum.filmorate.utils.validation.constraints;

import ru.yandex.practicum.filmorate.utils.validation.impl.NoSpacesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NoSpacesValidator.class})
public @interface NoSpaces {
    String message() default "строка не должна содержать пробелы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
