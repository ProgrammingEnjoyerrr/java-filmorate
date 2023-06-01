package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTests {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static Validator validator;

    @BeforeAll
    private static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCreateUserWithFailLoginShouldThrow() {
        final User user = new User("dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", formatter));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("строка не должна содержать пробелы", violations.iterator().next().getMessage());
    }

    @Test
    void testCreateUserWithFailEmailShouldThrow() {
        final User user = new User("valid_login", "", "mail.ru",
                LocalDate.parse("1980-08-20", formatter));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("должно иметь формат адреса электронной почты", violations.iterator().next().getMessage());
    }

    @Test
    void testCreateUserWithFailBirthdayShouldThrow() {
        final User user = new User("valid_login", "", "test@mail.ru",
                LocalDate.parse("2446-08-20", formatter));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("дата не может быть в будущем", violations.iterator().next().getMessage());
    }
}
