package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTests {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static Validator validator;

    private static final int ANY_ID = 0;

    @BeforeAll
    private static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCreateUserWithFailLoginShouldFailValidation() {
        final User user = new User(ANY_ID, "dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", FORMATTER));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("строка не должна содержать пробелы", violations.iterator().next().getMessage());
    }

    @Test
    void testCreateUserWithFailEmailShouldFailValidation() {
        final User user = new User(ANY_ID, "valid_login", "", "mail.ru",
                LocalDate.parse("1980-08-20", FORMATTER));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("должно иметь формат адреса электронной почты", violations.iterator().next().getMessage());
    }

    @Test
    void testCreateUserWithFailBirthdayShouldFailValidation() {
        final User user = new User(ANY_ID, "valid_login", "", "test@mail.ru",
                LocalDate.parse("2446-08-20", FORMATTER));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("дата не может быть в будущем", violations.iterator().next().getMessage());
    }
}
