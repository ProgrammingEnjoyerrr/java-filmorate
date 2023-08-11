package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDbStorageTests {

    private final UserDbStorage userStorage;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final int ANY_ID = 0;

    @Test
    @Order(1)
    void createUserShouldBeOk() {
        final User user = new User(ANY_ID, "dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", FORMATTER));

        final User created = userStorage.createUser(user);
        assertEquals(1, created.getId());
        assertEquals(user.getLogin(), created.getLogin());
        assertEquals(user.getName(), created.getName());
        assertEquals(user.getEmail(), created.getEmail());
        assertEquals(user.getBirthday(), created.getBirthday());
        assertEquals(user.getFriends(), created.getFriends());
    }

    @Test
    void updateUserShouldBeOk() {

    }

    @Test
    @Order(2)
    void getUserByIdShouldBeOk() {
        final User got = userStorage.getUserById(1);

        final User expected = new User(1, "dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", FORMATTER));

        assertEquals(expected.getId(), got.getId());
        assertEquals(expected.getLogin(), got.getLogin());
        assertEquals(expected.getName(), got.getName());
        assertEquals(expected.getEmail(), got.getEmail());
        assertEquals(expected.getBirthday(), got.getBirthday());
        assertEquals(expected.getFriends(), got.getFriends());
    }

    @Test
    void getUsersShouldBeOk() {

    }

    @Test
    void addFriendShouldBeOk() {

    }

    @Test
    void deleteFriendShouldBeOk() {

    }
}
