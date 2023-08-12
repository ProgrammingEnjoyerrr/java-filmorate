package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureTestDatabase
class UserControllerTests {

    @Autowired
    private UserController userController;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final int ANY_ID = 0;

    @Test
    @Order(1)
    void createUserShouldBeOk() {
        final User user = new User(ANY_ID, "dolore", "Nick Name", "mail@mail.ru",
                LocalDate.parse("1946-08-20", FORMATTER));

        final User created = userController.create(user);

        assertEquals(1, created.getId());
        assertEquals(created.getLogin(), user.getLogin());
        assertEquals(created.getName(), user.getName());
        assertEquals(created.getEmail(), user.getEmail());
        assertEquals(created.getBirthday(), user.getBirthday());
    }

    @Test
    @Order(2)
    void updateUserShouldBeOk() {
        final User user = new User(1, "doloreUpdate", "est adipisicing", "mail@yandex.ru",
                LocalDate.parse("1976-09-20", FORMATTER));

        final User updated = userController.update(user);

        assertEquals(1, updated.getId());
        assertEquals(updated.getLogin(), user.getLogin());
        assertEquals(updated.getName(), user.getName());
        assertEquals(updated.getEmail(), user.getEmail());
        assertEquals(updated.getBirthday(), user.getBirthday());
    }

    @Test
    @Order(3)
    void updateUnknownUserShouldThrow() {
        final User user = new User(9999, "doloreUpdate", "est adipisicing", "mail@yandex.ru",
                LocalDate.parse("1976-09-20", FORMATTER));

        final UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userController.update(user));
        assertEquals("Попытка обновить пользователя с несуществующим id 9999", exception.getMessage());
    }

    @Test
    @Order(4)
    void getAllUsersShouldBeOk() {
        final Collection<User> users = userController.getAllUsers();
        assertEquals(1, users.size());

        final User user = users.stream().limit(1).collect(Collectors.toList()).get(0);

        assertEquals("doloreUpdate", user.getLogin());
        assertEquals("est adipisicing", user.getName());
        assertEquals("mail@yandex.ru", user.getEmail());
        assertEquals(LocalDate.of(1976, 9, 20), user.getBirthday());
    }

    @Test
    @Order(5)
    void createUserWithEmptyNameShouldBeOk() {
        final User user = new User(ANY_ID, "common", "", "friend@common.ru",
                LocalDate.parse("2000-08-20", FORMATTER));

        final User created = userController.create(user);

        assertEquals(2, created.getId());
        assertEquals(created.getLogin(), user.getLogin());
        assertEquals(created.getName(), user.getName());
        assertEquals("common", created.getName());
        assertEquals(created.getEmail(), user.getEmail());
        assertEquals(created.getBirthday(), user.getBirthday());
    }

    @Test
    @Order(6)
    void createUserWithFailLoginShouldThrow() {
        final User user = new User(ANY_ID, "dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", FORMATTER));

        assertThrows(ConstraintViolationException.class, () -> userController.create(user));
    }

    @Test
    @Order(7)
    void createUserWithFailEmailShouldThrow() {
        final User user = new User(ANY_ID, "valid_login", "", "mail.ru",
                LocalDate.parse("1980-08-20", FORMATTER));

        assertThrows(ConstraintViolationException.class, () -> userController.create(user));
    }

    @Test
    @Order(8)
    void createUserWithFailBirthdayShouldThrow() {
        final User user = new User(ANY_ID, "valid_login", "", "test@mail.ru",
                LocalDate.parse("2446-08-20", FORMATTER));

        assertThrows(ConstraintViolationException.class, () -> userController.create(user));
    }

    @Test
    @Order(9)
    void updateUserWithFailLoginShouldThrow() {
        final User user = new User(1, "dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", FORMATTER));

        assertThrows(ConstraintViolationException.class, () -> userController.update(user));
    }

    @Test
    @Order(10)
    void updateUserWithFailEmailShouldThrow() {
        final User user = new User(1, "valid_login", "", "mail.ru",
                LocalDate.parse("1980-08-20", FORMATTER));

        assertThrows(ConstraintViolationException.class, () -> userController.update(user));
    }

    @Test
    @Order(11)
    void updateUserWithFailBirthdayShouldThrow() {
        final User user = new User(1, "valid_login", "", "test@mail.ru",
                LocalDate.parse("2446-08-20", FORMATTER));

        assertThrows(ConstraintViolationException.class, () -> userController.update(user));
    }
}
