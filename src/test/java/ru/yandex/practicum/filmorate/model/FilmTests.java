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

class FilmTests {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static Validator validator;

    private static final int ANY_ID = 0;

    @BeforeAll
    private static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCreateFilmWithFailNameShouldFailValidation() {
        final Film film = new Film(ANY_ID, "", "Description", LocalDate.parse("1900-03-25", FORMATTER), 200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("не должно быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void testCreateFilmWithFailDescriptionShouldFailValidation() {
        final Film film = new Film(ANY_ID, "Film name", "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.",
                LocalDate.parse("1900-03-25", FORMATTER), 200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("превышено максимальное допустимое количество символов в строке", violations.iterator().next().getMessage());
    }

    @Test
    void testCreateFilmWithFailReleaseDateShouldFailValidation() {
        final Film film = new Film(ANY_ID, "Name", "Description", LocalDate.parse("1890-03-25", FORMATTER), 200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("дата не может быть раньше чем заданная", violations.iterator().next().getMessage());
    }

    @Test
    void testCreateFilmWithFailDurationShouldFailValidation() {
        final Film film = new Film(ANY_ID, "Name", "Description", LocalDate.parse("1980-03-25", FORMATTER), -200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("должно быть больше 0", violations.iterator().next().getMessage());
    }
}
