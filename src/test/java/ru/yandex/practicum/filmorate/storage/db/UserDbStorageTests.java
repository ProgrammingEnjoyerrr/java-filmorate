package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
//@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTests {

    private final UserDbStorage userStorage;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final int ANY_ID = 0;

    @Test
    void testInsert() {
        final User user = new User(ANY_ID, "dolore ullamco", "some name", "yandex@mail.ru",
                LocalDate.parse("1946-08-20", FORMATTER));

        final User created = userStorage.createUser(user);
        int foo = 42;
    }
}
