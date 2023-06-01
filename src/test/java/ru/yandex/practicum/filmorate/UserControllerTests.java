package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTests {

    private static UserController userController;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static Validator validator;

    @BeforeAll
    private static void init() {
        userController = new UserController();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Order(1)
    void testCreateUserShouldBeOk() {
        final User user = new User("dolore", "Nick Name", "mail@mail.ru",
                LocalDate.parse("1946-08-20", formatter));

        final User created = userController.create(user);

        assertEquals(1, created.getId());
        assertEquals(created.getLogin(), user.getLogin());
        assertEquals(created.getName(), user.getName());
        assertEquals(created.getEmail(), user.getEmail());
        assertEquals(created.getBirthday(), user.getBirthday());
    }

    @Test
    @Order(2)
    void testCreateUserWithFailLoginShouldThrow() {
        final User user = new User("dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", formatter));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("строка не должна содержать пробелы", violations.iterator().next().getMessage());
    }

    @Test
    @Order(3)
    void testCreateUserWithFailEmailShouldThrow() {
        final User user = new User("valid_login", "", "mail.ru",
                LocalDate.parse("1980-08-20", formatter));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("должно иметь формат адреса электронной почты", violations.iterator().next().getMessage());
    }

    @Test
    @Order(4)
    void testCreateUserWithFailBirthdayShouldThrow() {
        final User user = new User("valid_login", "", "test@mail.ru",
                LocalDate.parse("2446-08-20", formatter));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("дата не может быть в будущем", violations.iterator().next().getMessage());
    }

    @Test
    @Order(5)
    void testUpdateUserShouldBeOk() {
        final User user = new User(1, "doloreUpdate", "est adipisicing", "mail@yandex.ru",
                LocalDate.parse("1976-09-20", formatter));

        final User updated = userController.update(user);

        assertEquals(1, updated.getId());
        assertEquals(updated.getLogin(), user.getLogin());
        assertEquals(updated.getName(), user.getName());
        assertEquals(updated.getEmail(), user.getEmail());
        assertEquals(updated.getBirthday(), user.getBirthday());
    }

    @Test
    @Order(6)
    void testUpdateUnknownUserShouldThrow() {
        final User user = new User(9999, "doloreUpdate", "est adipisicing", "mail@yandex.ru",
                LocalDate.parse("1976-09-20", formatter));

        final UserValidationException exception = assertThrows(UserValidationException.class,
                () -> userController.update(user));
        assertEquals("Попытка обновить пользователя с несуществующим id 9999", exception.getMessage());
    }

    @Test
    @Order(7)
    void testGetAllUsersShouldBeOk() {
        final Collection<User> users = userController.getAllUsers();
        assertEquals(1, users.size());

        final User user = users.stream().limit(1).collect(Collectors.toList()).get(0);

        assertEquals("doloreUpdate", user.getLogin());
        assertEquals("est adipisicing", user.getName());
        assertEquals("mail@yandex.ru", user.getEmail());
        assertEquals(LocalDate.of(1976, 9, 20), user.getBirthday());
    }

    @Test
    @Order(8)
    void testCreateUserWithEmptyNameShouldBeOk() {
        final User user = new User("common", "", "friend@common.ru",
                LocalDate.parse("2000-08-20", formatter));

        final User created = userController.create(user);

        assertEquals(2, created.getId());
        assertEquals(created.getLogin(), user.getLogin());
        assertEquals(created.getName(), user.getName());
        assertEquals("common", created.getName());
        assertEquals(created.getEmail(), user.getEmail());
        assertEquals(created.getBirthday(), user.getBirthday());
    }
}
