package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
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
    @Order(3)
    void getUsersShouldBeOk() {
        List<User> users = new ArrayList<>(userStorage.getUsers());
        assertNotNull(users);
        assertEquals(1, users.size());

        final User got = users.get(0);

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
    @Order(4)
    void updateUserShouldBeOk() {
        final User updated = new User(1, "login", "name", "llvm@mail.ru",
                LocalDate.parse("1999-08-20", FORMATTER));

        final User got = userStorage.updateUser(updated);

        assertEquals(updated.getId(), got.getId());
        assertEquals(updated.getLogin(), got.getLogin());
        assertEquals(updated.getName(), got.getName());
        assertEquals(updated.getEmail(), got.getEmail());
        assertEquals(updated.getBirthday(), got.getBirthday());
        assertEquals(updated.getFriends(), got.getFriends());
    }

    @Test
    void addAndDeleteFriendShouldBeOk() {
        User biba = new User(ANY_ID, "bibazavr", "biba", "biba@mail.ru",
                LocalDate.parse("1950-01-01", FORMATTER));
        biba = userStorage.createUser(biba);

        User boba = new User(ANY_ID, "bobazavr", "boba", "boba@mail.ru",
                LocalDate.parse("1950-01-01", FORMATTER));
        boba = userStorage.createUser(boba);

        userStorage.addFriend(biba.getId(), boba.getId());

        userStorage.deleteFriend(biba.getId(), boba.getId());
    }
}
