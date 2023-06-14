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
    void createFilmWithFailNameShouldFailValidation() {
        final Film film = new Film(ANY_ID, "", "Description", LocalDate.parse("1900-03-25", FORMATTER), 200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    void createFilmWithFailDescriptionShouldFailValidation() {
        final Film film = new Film(ANY_ID, "Film name", "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.",
                LocalDate.parse("1900-03-25", FORMATTER), 200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    void createFilmWithFailReleaseDateShouldFailValidation() {
        final Film film = new Film(ANY_ID, "Name", "Description", LocalDate.parse("1890-03-25", FORMATTER), 200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    void createFilmWithFailDurationShouldFailValidation() {
        final Film film = new Film(ANY_ID, "Name", "Description", LocalDate.parse("1980-03-25", FORMATTER), -200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }
}
